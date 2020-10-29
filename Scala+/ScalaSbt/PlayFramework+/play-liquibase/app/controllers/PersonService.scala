package controllers

import java.util.concurrent.TimeUnit

import javax.inject.{Inject, Singleton}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

@Singleton
class PersonService @Inject()(private val personDao: PersonDao) {
  def getAll: Seq[Person] = personDao.getAll

  def create(person: Person): Unit = Await.result(personDao.create(person), Duration(3, TimeUnit.SECONDS))

  def delete(personId: Int): Unit = Await.result(personDao.delete(personId), Duration(3, TimeUnit.SECONDS))
}
