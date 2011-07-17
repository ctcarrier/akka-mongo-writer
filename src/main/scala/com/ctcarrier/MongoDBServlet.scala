package com.ctcarrier

import org.scalatra._
import com.mongodb.casbah.Imports._
import com.ctcarrier.writer.PersistenceActor
import akka.actor.{Actor, PoisonPill}
import Actor._
import akka.routing.{Routing, CyclicIterator}
import Routing._
import net.liftweb.json.DefaultFormats

//import net.liftweb.json._
import net.liftweb.json.JsonParser._
import com.ctcarrier.model._

class MongoDBServlet extends ScalatraServlet {
  implicit val formats = DefaultFormats

  val persistenceActor = actorOf(new PersistenceActor()).start()

  post("/widgets") {
    val widget = parse(request.body).extract[Widget]
    persistenceActor ! widget
    "Success"
  }

  post("/documents") {
    val document = parse(request.body).extract[Document]
    persistenceActor ! document
    "Success"
  }

}
