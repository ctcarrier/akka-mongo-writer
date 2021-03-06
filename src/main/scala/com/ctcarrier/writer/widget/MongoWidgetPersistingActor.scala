package com.ctcarrier.writer.widget

import akka.actor.{Actor, PoisonPill}
import Actor._
import akka.routing.{Routing, CyclicIterator}
import Routing._
import com.mongodb.casbah.Imports._
import com.ctcarrier.model._
import akka.event.EventHandler
import akka.dispatch._
import com.novus.salat._
import com.novus.salat.global._

/**
 * @author chris_carrier
 * @version 6/30/11
 */


class MongoWidgetPersistingActor(dispatcher: MessageDispatcher, collection: MongoCollection) extends Actor {
  val mongo = collection

  self.dispatcher = dispatcher

  def receive = {
    case action: Widget => {
      val dbo = grater[Widget].asDBObject(action)
      mongo += dbo
    }
    case _ => EventHandler.info(this, "received unknown message")
  }
}