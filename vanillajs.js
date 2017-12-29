'use strict';

module.exports.hello = (context, req) => {
  const response = {
    status: 200,
    body: JSON.stringify({
      message: 'Go Serverless v1.0! Your function executed successfully!',
      input: req,
    }),
  };

  context.done(null, response);
};
