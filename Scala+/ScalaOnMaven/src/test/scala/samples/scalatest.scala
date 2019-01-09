package samples

import org.scalatest.FunSpec

import scala.collection.mutable

class ExampleSpec extends FunSpec {

  describe("An ArrayStack") {

    it("should pop values in last-in-first-out order") {
      val stack = new mutable.ArrayStack[Int]
      stack.push(1)
      stack.push(2)
      assert(stack.pop() === 2)
      assert(stack.pop() === 1)
    }

    it("should throw RuntimeException if an empty array stack is popped") {
      val emptyStack = new mutable.ArrayStack[Int]
      intercept[RuntimeException] {
        emptyStack.pop()
      }
    }
  }
}
