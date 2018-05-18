const mysqlConnectionSettings = require('./mysqlConnectionSettings.json')
const express = require('express')
const mysql = require('mysql')
const app = express()

const INSERT_QUERY = 'INSERT INTO test.t1 SET ?'
const AGGRAGATE_QUERY =
  "SELECT message, COUNT(message) as 'count' FROM test.t1 GROUP BY message"
const MESSAGES = ['Hello, World!', 'foobar', 'fizzbuzz', "That's not a bug..."]

const randFibInput = () => Math.floor(Math.random() * 8) + 30

const fib = n => (n <= 1 ? 1 : fib(n - 1) + fib(n - 2))

const randomMessage = () =>
  MESSAGES[Math.floor(Math.random() * MESSAGES.length)]

var con = mysql.createConnection(mysqlConnectionSettings)

con.connect(function(err) {
  if (err) throw err
  console.log('Connected!')
})

app.get('/noop', (req, res) => res.send('Hello World!'))

app.get('/cpu', (req, res) => {
  const input = randFibInput()
  res.send(`fib(${input}) = ${fib(input)}`)
})

app.get('/sleep', (req, res) =>
  setTimeout(() => res.send('HEY, WAKE UP!'), 500),
)

app.get('/write', (req, res) => {
  const data = { message: randomMessage() }
  con.query(INSERT_QUERY, data, function(err, result) {
    if (err) throw err
    data.id = result.insertId
    res.send(data)
  })
})

app.get('/read', (req, res) => {
  con.query(AGGRAGATE_QUERY, (err, result) => {
    const data = {}
    result.forEach(r => (data[r.message] = r.count))
    res.send(data)
  })
})

app.listen(13031, () => console.log('Example app listening on port 13031!'))
