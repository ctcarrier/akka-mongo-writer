package com.ctcarrier.boot

/**
 * @author chris_carrier
 * @version 7/7/11
 */


import akka.util.AkkaLoader
import javax.servlet.{ServletContextListener, ServletContextEvent}
import akka.actor.{Supervisor, BootableActorLoaderService, Actor}
import akka.config.Supervision
import Supervision._
import Actor._
import com.mongodb.casbah.MongoConnection
import com.ctcarrier.writer.document.MongoDocumentPersistingActor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import akka.dispatch._
import org.squeryl.SessionFactory
import org.squeryl.Session
import com.ctcarrier.schema.Library
import org.squeryl.adapters.PostgreSqlAdapter
import com.ctcarrier.writer.widget.{DbWidgetPersistingActor, MongoWidgetPersistingActor}

/**
  * This class can be added to web.xml mappings as a listener to start and postStop Akka.
  *<web-app>
  * ...
  *  <listener>
  *    <listener-class>com.my.Initializer</listener-class>
  *  </listener>
  * ...
  *</web-app>
  */
class Initializer extends ServletContextListener {

  val logger = LoggerFactory.getLogger("com.ctcarrier.boot.Initializer");

  //logger.info("Schema: " + Library.printDdl);

  Class.forName("org.postgresql.Driver");
  SessionFactory.concreteFactory = Some(()=>
	    Session.create(
	      java.sql.DriverManager.getConnection("jdbc:postgresql://localhost/widget_dev", "eng", ""),
	      new PostgreSqlAdapter))

  val widgetMongoWorkStealingDispatcher = Dispatchers.newExecutorBasedEventDrivenWorkStealingDispatcher("widget-mongo-pooled-dispatcher").build
  val widgetDbWorkStealingDispatcher = Dispatchers.newExecutorBasedEventDrivenWorkStealingDispatcher("widget-db-pooled-dispatcher").build
  val documentWorkStealingDispatcher = Dispatchers.newExecutorBasedEventDrivenWorkStealingDispatcher("document-pooled-dispatcher").build

  val db = MongoConnection("localhost",27017)("akkaTest")

  val mongoWidgetActor = actorOf(new MongoWidgetPersistingActor(widgetMongoWorkStealingDispatcher, db("widgets"))).start()
  val dbWidgetActor = actorOf(new DbWidgetPersistingActor(widgetDbWorkStealingDispatcher)).start()
  val documentActor = actorOf(new MongoDocumentPersistingActor(documentWorkStealingDispatcher, db("documents"))).start()

  val supervisor = Supervisor(
    SupervisorConfig(
      OneForOneStrategy(List(classOf[Exception]), 3, 1000),
      Supervise(
        mongoWidgetActor,
        Permanent) ::
      Supervise(
        dbWidgetActor,
        Permanent) ::
      Supervise(
        documentActor,
        Permanent) ::
      Nil))

   lazy val loader = new AkkaLoader
   def contextDestroyed(e: ServletContextEvent): Unit = loader.shutdown
   def contextInitialized(e: ServletContextEvent): Unit =
//     loader.boot(true, new BootableActorLoaderService with BootableRemoteActorService) //<--- Important
     loader.boot(true, new BootableActorLoaderService {}) // If you don't need akka-remote
 }
