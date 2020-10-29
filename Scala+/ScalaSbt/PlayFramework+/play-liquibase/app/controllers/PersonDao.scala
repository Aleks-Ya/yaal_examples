package controllers

import javax.inject.{Inject, Singleton}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class PersonDao @Inject()() {

  def getAll: Future[Seq[Person]] = Future(Seq(Person(1, "John")))

  def create(person: Person): Future[Unit] = Future()

  def delete(personId: Int): Future[Int] = Future(33)
}
