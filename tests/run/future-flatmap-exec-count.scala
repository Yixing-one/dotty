// scalajs: --skip

import scala.concurrent.*
import java.util.concurrent.atomic.AtomicInteger

object Test {
  def main(args: Array[String]): Unit = {
    test()
  }

  def test() = {
    def await(f: Future[Any]) =
      Await.result(f, duration.Duration.Inf)

    val ec = new TestExecutionContext(ExecutionContext.Implicits.global)

    {
      val p = Promise[Int]()
      val fp = p.future
      println("mapping")
      val mapped = fp.map(x => x)(ec)
      p.success(0)
      await(mapped)
    }

    {
      println("flatmapping")
      val p = Promise[Int]()
      val fp = p.future
      val flatMapped = fp.flatMap({ (x: Int) =>
        Future.successful(2 * x)
      })(ec)
      p.success(0)
      await(flatMapped)
    }

    {
      println("recovering")
      val recovered = Future.failed(new Throwable()).recoverWith {
        case _ => Future.successful(2)
      }(ec)
      await(recovered)
    }
  }

  class TestExecutionContext(deleg: ExecutionContext) extends ExecutionContext {
    def execute(runnable: Runnable): Unit = ???

    def reportFailure(t: Throwable): Unit = ???

    override def prepare(): ExecutionContext = {
      val preparedDelegate = deleg.prepare()
      return new ExecutionContext {
        def execute(runnable: Runnable): Unit = {
          println("execute()")
          preparedDelegate.execute(runnable)
        }

        def reportFailure(t: Throwable): Unit = ???
      }
    }
  }
}
