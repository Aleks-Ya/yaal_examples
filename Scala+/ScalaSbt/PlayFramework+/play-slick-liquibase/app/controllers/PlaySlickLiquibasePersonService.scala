package controllers

import java.util.concurrent.TimeUnit

import javax.inject.{Inject, Singleton}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

@Singleton
class PlaySlickLiquibasePersonService @Inject()(private val personDao: PlaySlickLiquibasePersonDao) {
  def getAll: Seq[PlaySlickLiquibasePerson] = Await.result(personDao.getAll, Duration(3, TimeUnit.SECONDS))

  def create(person: PlaySlickLiquibasePerson): Unit = Await.result(personDao.create(person), Duration(3, TimeUnit.SECONDS))

  def delete(personId: Int): Unit = Await.result(personDao.delete(personId), Duration(3, TimeUnit.SECONDS))
}
