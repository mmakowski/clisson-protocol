package com.bimbr.clisson.protocol

import java.io.File
import scala.collection.mutable.ArrayBuffer

import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

import com.bimbr.clisson.protocol.Types._

@RunWith(classOf[JUnitRunner])
class TypesSpec extends Specification {
  "Types" should {
    "throw `IllegalArgumentException` for usnupported type name" in {
      classFor("unknowntype").asInstanceOf[Class[Any]] must throwAn [IllegalArgumentException]
    }
    "provide class for each implementation of `StandaloneObject`" in {
      val standaloneObjectClasses = classesIn("com.bimbr.clisson.protocol") filter { c => 
        !c.isInterface && classOf[StandaloneObject].isAssignableFrom(c)
      }
      standaloneObjectClasses.map(id).map(classFor) mustEqual standaloneObjectClasses
    }
  }
  
  private def classesIn(packageName: String): Seq[Class[_]] = {
    val classLoader = Thread.currentThread.getContextClassLoader
    val path = packageName replace ('.', '/')
    val resources = classLoader getResources (path)
    val dirs = ArrayBuffer[File]()
    while (resources hasMoreElements) {
      val resource = resources.nextElement()
      dirs += new File(resource.getFile())
    }
    dirs.flatMap(classesIn(_, packageName))
  }

  private def classesIn(directory: File, packageName: String): Seq[Class[_]] =
    directory.listFiles.toSeq.map(_.getName).filter(_.endsWith(".class")) map { fn => 
      Class.forName(packageName + '.' + fn.substring(0, fn.length - 6))
    }
  
}