package pureslick

import org.jbox2d.dynamics.Body
import org.newdawn.slick.Image

/**
 * Created with IntelliJ IDEA.
 * User: peter
 * Date: 6/30/12
 * Time: 4:06 PM
 * To change this template use File | Settings | File Templates.
 */

trait GameObject extends Renderable{
  var isGarbage = false
  val body: Body
  var image: Image

  def update = {}

  def update(observation: Observation): Unit = {update}


  def render() = {
    image.draw((body.getPosition.x - image.getWidth/20)*10, (body.getPosition.y - image.getHeight/20)*10)
  }


}
