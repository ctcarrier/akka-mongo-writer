package com.ctcarrier.writer.document

import akka.actor.{Actor, PoisonPill}
import Actor._
import akka.routing.{Routing, CyclicIterator}
import Routing._
import com.mongodb.casbah.Imports._
import com.ctcarrier.model._
import akka.event.EventHandler
import com.novus.salat._
import com.novus.salat.global._
import akka.dispatch._

/**
 * @author chris_carrier
 * @version 6/30/11
 */


class MongoDocumentPersistingActor(dispatcher: MessageDispatcher, collection: MongoCollection) extends Actor {
  val mongo = collection

  def receive = {
    case conversion: Document => {
      val dbo = grater[Document].asDBObject(conversion)
      mongo += dbo
    }
    case _ => EventHandler.info(this, "received unknown message")
  }
}