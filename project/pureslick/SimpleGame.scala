package pureslick

import org.newdawn.slick.{Image, AppGameContainer, BasicGame, GameContainer}
import org.jbox2d.dynamics.World
import org.jbox2d.common.Vec2

/**
 * Created with IntelliJ IDEA.
 * User: peter
 * Date: 6/13/12
 * Time: 1:47 AM
 * To change this template use File | Settings | File Templates.
 */

object SimpleGame extends BasicGame("Zombie") {
  var player: Player = null
  var z: Zombie = null
  val b2World = new World(new Vec2(0f, 0f), true)
  var crosshairs: Image = null
  var wall: Wall = null

  def init(gc: GameContainer) = {
    player = new Player(new Image("data/player.png"), b2World)
    z = new Zombie(b2World)
    wall = new Wall(b2World, new Vec2(30f, 30f))
    gc.setMouseCursor(new Image("data/crosshairs.png"), 20, 20)

    b2World.setContactListener(new BulletContactCallback)
    //debug draw
    val sDD = new Slick2dDebugDraw(gc.getGraphics,gc)
    sDD.setFlags(0x0001)
    b2World.setDebugDraw(sDD)
  }

  def update(gc: GameContainer, delta: Int) = {
    val input = gc.getInput
    player.handleInput(input)
    b2World.step(1 / 60f, 6, 2)
    player.update()
  }

  def render(gc: GameContainer, g: org.newdawn.slick.Graphics) = {
    g.translate(400 - player.body.getPosition.x, 300 - player.body.getPosition.y)
    player.render()
    z.render()
    b2World.drawDebugData()
    wall.render()
  }

  def main(args: Array[String]) = {
    val app = new AppGameContainer(this)
    app.setDisplayMode(800, 600, false)
    app.start()
  }


}
