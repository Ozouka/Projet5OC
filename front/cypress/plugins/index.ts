/// <reference types="cypress" />

const coverageTask = require('@cypress/code-coverage/task')

module.exports = (on: any, config: any) => {
  coverageTask(on, config)
  return config
}
