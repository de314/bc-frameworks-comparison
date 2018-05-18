package com.bettercloud.perf.naive;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@SpringBootApplication
public class NaiveSpringBootApplication {

    private static final String INSERT_QUERY = "INSERT INTO t1 (message) VALUES (?)";
    private static final String AGGRAGATE_QUERY = "SELECT message, COUNT(message) as 'count' FROM t1 GROUP BY message";
    private static final String[] MESSAGES = new String[] {
            "Hello, World!",
            "foobar",
            "fizzbuzz",
            "That's not a bug..."
    };

	public static void main(String[] args) {
		SpringApplication.run(NaiveSpringBootApplication.class, args);
	}

	@Autowired
	private JdbcTemplate jdbcTemplate;
	private ObjectMapper jsonObjectMapper = new ObjectMapper();

	private int randFibInput() {
	    return (int) (Math.floor(Math.random() * 8) + 30);
    }

    private long fib(long n) {
	    return n <= 1 ? 1 : fib(n-1) + fib(n-2);
    }

    private String randomMessage() {
        return MESSAGES[(int) (Math.random() * MESSAGES.length)];
    }

	@GetMapping("/noop")
	public String noop() {
		return "Hello, World";
	}

	@GetMapping("/cpu")
	public String cpu() {
	    int input = randFibInput();
		return String.format("fib(%d) = %d", input, fib(input));
	}

	@GetMapping("/sleep")
    public String sleep() throws InterruptedException {
	    Thread.sleep(500);
	    return "HEY, WAKE UP!";
    }
    
    @GetMapping("/write")
    public JsonNode write() {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final String message = randomMessage();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement ps = con.prepareStatement(INSERT_QUERY, new String[] {"id"});
                        ps.setString(1, message);
                        return ps;
                    }
                },
                keyHolder
        );
        return jsonObjectMapper.createObjectNode()
                .put("id", (long)keyHolder.getKey())
                .put("message", message);
    }

    @GetMapping("/read")
    public Map<String, Integer> read() {
	    return jdbcTemplate.query(AGGRAGATE_QUERY, new ResultSetExtractor<Map<String, Integer>>() {
            @Nullable
            @Override
            public Map<String, Integer> extractData(ResultSet rs) throws SQLException, DataAccessException {
                Map<String, Integer> result = new HashMap<>();
                while (rs.next()) {
                    result.put(
                        rs.getString("message"),
                        rs.getInt("count")
                    );
                }
                return result;
            }
        });
    }
}
