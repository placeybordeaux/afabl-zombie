package pureslick

import org.newdawn.slick.{Image, AppGameContainer, BasicGame, GameContainer}
import org.jbox2d.dynamics.World
import org.jbox2d.common.Vec2
import org.newdawn.slick.tiled.TiledMap
import util.Random

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
  var background: List[Grass] = List()
  var level = 1f

  def init(gc: GameContainer) = {
    for(x <- 0 to 10)
      for (y <- 0 to 10)
        background ::= new Grass(x*100,y*80)
    background = background.sortWith(_.y < _.y)

    createWave(level)
    gc.setMouseCursor(new Image("data/crosshairs.png"), 20, 20)
    b2World.setContactListener(new ContactCallbacks)
  }

  def clearWave() = {
    var body = b2World.getBodyList
    while (body != null){
      b2World.destroyBody(body)
      body = body.getNext
    }

    gameObjects = List()
  }

  def createWave(level: Float) = {
    player = new Player(new Image("data/player.png"), b2World)
    gameObjects ::= player

    for(x <- 0 to 10){
      for (y <- 0 to 10){
        if (y < 10){
          if (Random.nextFloat() > .9)
            gameObjects ::= new Wall(b2World,x*10,y*8)
          if (Random.nextFloat() > .8 + level/100)
            gameObjects ::= new Clip(b2World,x*10,y*8)
          if (Random.nextFloat() > .8)
            gameObjects ::= new NPC(b2World,x*10,y*8)
          if (Random.nextFloat() > .98 - level/100 && x+y > 4)
            gameObjects ::= new Zombie(b2World,x*10,y*8)
        }
          }
    }
    //left and right walls
    for (y <- -1 to 10){
      gameObjects ::= new Wall(b2World,-10,y*8)
      gameObjects ::= new Wall(b2World,110,y*8)
    }
    //top and bottom walls
    for (x <- -1 to 10){
      gameObjects ::= new Wall(b2World,x*10,-16)
      gameObjects ::= new Wall(b2World,x*10,80)
    }

  }

  def update(gc: GameContainer, delta: Int) = {
    if (gameObjects.filter(_.isInstanceOf[Zombie]).size == 0){
      level += 1
      clearWave
      createWave(level)
    }
    //simulate
    val observation = new Observation(gameObjects.filter(_.isInstanceOf[Humanoid]).asInstanceOf[List[Humanoid]])
    val input = gc.getInput
    player.handleInput(input)
    gameObjects.foreach(_.update(observation))
    player.update()
    b2World.step(1 / 60f, 6, 2)

    //create random ammo

    if(Random.nextFloat > 0.98){
      gameObjects ::= new Clip(b2World, Random.nextInt(10*10), Random.nextInt(8*10),Random.nextInt(30))
    }

    //clean up
    cleanup

  }

  def cleanup() = {
    for(garbage <- gameObjects.filter(_.isGarbage)){
      garbage match {
        case human: Human =>
          gameObjects ::= new Zombie(b2World, human.body.getPosition)
          b2World.destroyBody(human.body)
          if (human.ammo>0) new Clip(b2World, human.body.getPosition,human.ammo)
        case zombie: Zombie =>
          b2World.destroyBody(zombie.body)
        case _ =>
          b2World.destroyBody(garbage.body)
      }
    }
    gameObjects = gameObjects.filterNot(_.isGarbage)
    //TODO make this look better
  }

  def render(gc: GameContainer, g: org.newdawn.slick.Graphics) = {
    g.translate(400 - player.body.getPosition.x*10, 300 - player.body.getPosition.y*10)
    background.foreach(_.render)
    gameObjects = gameObjects.sortWith(_.body.getPosition.y < _.body.getPosition.y)
    gameObjects.foreach(_.render)
    //b2World.drawDebugData()
    g.translate(-400 + player.body.getPosition.x*10, -300 + player.body.getPosition.y*10)
    g.drawString("Ammo:" + player.ammo.toString,0,0)
  }

  def main(args: Array[String]) = {
    val app = new AppGameContainer(this)
    app.setDisplayMode(800, 600, false)
    app.start()
  }


}
