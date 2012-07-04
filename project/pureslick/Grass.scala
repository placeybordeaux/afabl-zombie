package pureslick

import org.newdawn.slick.Image

/**
 * Created with IntelliJ IDEA.
 * User: peter
 * Date: 7/3/12
 * Time: 8:54 PM
 * To change this template use File | Settings | File Templates.
 */

class Grass(X: Float, Y:Float) extends Renderable {
  val x = X
  val y = Y
  val img = new Image("data/PlanetCute PNG/Grass Block.png")
  def render = {
    img.draw(x - img.getWidth/2,y - img.getHeight - 2)
  }

}
