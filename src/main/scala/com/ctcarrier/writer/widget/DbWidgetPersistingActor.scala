package com.ctcarrier.writer.widget

import akka.actor.{Actor, PoisonPill}
import Actor._
import akka.routing.{Routing, CyclicIterator}
import Routing._
import com.ctcarrier.model._
import akka.event.EventHandler
import akka.dispatch._
import com.ctcarrier.schema.Library
import org.squeryl.PrimitiveTypeMode._

/**
 * @author chris_carrier
 * @version 7/14/11
 */


class DbWidgetPersistingActor(dispatcher: MessageDispatcher) extends Actor {

  self.dispatcher = dispatcher

  override def preRestart(reason: Throwable) {
    EventHandler.info(this, "Restarting DB Action Persister")
  }

  def receive = {
    case widget: Widget => {
      import Library._
      transaction {
        widgets.insert(widget)
      }
    }
    case _ => EventHandler.info(this, "received unknown message")
  }
}