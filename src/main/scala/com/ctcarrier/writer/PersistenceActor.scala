package com.ctcarrier.writer

import akka.actor.{Actor, PoisonPill}
import Actor._
import akka.routing.{Routing, CyclicIterator}
import Routing._
import com.mongodb.casbah.Imports._
import com.ctcarrier.model._
import akka.event.EventHandler
import com.novus.salat._
import com.novus.salat.global._
import com.ctcarrier.writer.widget._
import com.ctcarrier.writer.document._
import akka.dispatch._




/**
 * @author chris_carrier
 * @version 6/20/11
 */


class PersistenceActor extends Actor {

  val widgetMongoActor = Actor.registry.actorsFor[MongoWidgetPersistingActor](classOf[MongoWidgetPersistingActor]).head
  val widgetDbActor = Actor.registry.actorsFor[DbWidgetPersistingActor](classOf[DbWidgetPersistingActor]).head
  val documentActor = Actor.registry.actorsFor[MongoDocumentPersistingActor](classOf[MongoDocumentPersistingActor]).head


  def receive = {
    case widget: Widget => {
      widgetMongoActor ! widget
      widgetDbActor ! widget
    }
    case document: Document => {
      documentActor ! document
    }
  }
}