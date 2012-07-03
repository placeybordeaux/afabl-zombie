package pureslick

import org.newdawn.slick.{Image, AppGameContainer, BasicGame, GameContainer}
import org.jbox2d.dynamics.World
import org.jbox2d.common.Vec2
import org.newdawn.slick.tiled.TiledMap

/**
 * Created with IntelliJ IDEA.
 * User: peter
 * Date: 6/13/12
 * Time: 1:47 AM
 * To change this template use File | Settings | File Templates.
 */

object SimpleGame extends BasicGame("Zombie") {
  var player: Player = null
  val b2World = new World(new Vec2(0f, 0f), true)
  var crosshairs: Image = null
  var gameObjects: List[GameObject] = List()
  def init(gc: GameContainer) = {
    player = new Player(new Image("data/player.png"), b2World)
    gameObjects ::= new Zombie(b2World)
    gameObjects ::= new Zombie(b2World, 200, 200)
    //gameObjects ::= new Wall(b2World, new Vec2(30f, 30f))
    gameObjects ::= player
    gameObjects ::= new NPC(b2World, 50,100)

    gameObjects ::= new NPC(b2World, 100,50)
    gc.setMouseCursor(new Image("data/crosshairs.png"), 20, 20)

    b2World.setContactListener(new BulletContactCallback)
    //debug draw
    val sDD = new Slick2dDebugDraw(gc.getGraphics,gc)
    sDD.setFlags(0x0001)
    b2World.setDebugDraw(sDD)
  }


  def update(gc: GameContainer, delta: Int) = {
    for(garbage <- gameObjects.filter(_.isGarbage)){
      if (garbage.isInstanceOf[Human]){
        gameObjects ::= new Zombie(b2World, garbage.body.getPosition)
      }
      b2World.destroyBody(garbage.body)
    }
    gameObjects = gameObjects.filterNot(_.isGarbage)
    //TODO make this look better
    val observation = new Observation(gameObjects.filter(_.isInstanceOf[Humanoid]).asInstanceOf[List[Humanoid]])
    val input = gc.getInput
    player.handleInput(input)
    gameObjects.foreach(_.update(observation))
    player.update()
    b2World.step(1 / 60f, 6, 2)
  }

  def render(gc: GameContainer, g: org.newdawn.slick.Graphics) = {

    val x = player.body.getPosition.x.toInt
    val y = player.body.getPosition.y.toInt
    g.translate(400 - player.body.getPosition.x, 300 - player.body.getPosition.y)



    gameObjects = gameObjects.sortWith(_.body.getPosition.y < _.body.getPosition.y)
    gameObjects.foreach(_.render)
  }

  def main(args: Array[String]) = {
    val app = new AppGameContainer(this)
    app.setDisplayMode(800, 600, false)
    app.start()
  }


}
