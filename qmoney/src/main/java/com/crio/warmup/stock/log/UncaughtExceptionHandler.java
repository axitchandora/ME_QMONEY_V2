
package com.crio.warmup.stock.log;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

  private static final Logger log = LogManager.getLogger(UncaughtExceptionHandler.class);

  @Override
  public void uncaughtException(Thread t, Throwable e) {
    ObjectNode logEventJsonObjNode = JsonNodeFactory.instance.objectNode();

    if (e.getStackTrace() != null && e.getStackTrace().length > 0) {
      ArrayNode logStacktraceJsonArrNode = JsonNodeFactory.instance.arrayNode();

      for (StackTraceElement stackTraceElement : e.getStackTrace()) {
        logStacktraceJsonArrNode.add(stackTraceElement.toString());
      }
      logEventJsonObjNode.set("stacktrace", logStacktraceJsonArrNode);
    }

    logEventJsonObjNode.put("cause", e.toString());

    log.error(logEventJsonObjNode.toString(), e);
  }
}
