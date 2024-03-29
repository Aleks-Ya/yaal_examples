package example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

// Handler value: example.HandlerString
public class HandlerString implements RequestHandler<String, Integer>{
  Gson gson = new GsonBuilder().setPrettyPrinting().create();
  @Override
  public Integer handleRequest(String event, Context context)
  {
    LambdaLogger logger = context.getLogger();
    // process event
    logger.log("EVENT 3: ".repeat(2) + gson.toJson(event));
    logger.log("EVENT TYPE 3: " + event.getClass().toString());
    return context.getRemainingTimeInMillis() ;
  }
}