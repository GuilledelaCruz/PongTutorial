pong = {}

buttons = {buttonplay = nil, buttonexit = nil}
width = nil
height = nil
buttonx = nil
buttony = nil

function love.load(arg)

	buttons.buttonplay = love.graphics.newImage("assets/images/buttonplay.png")
	buttons.buttonexit = love.graphics.newImage("assets/images/buttonexit.png")

	width = love.graphics.getWidth()
	height = love.graphics.getHeight()
	buttonx = width / 2 - buttons.buttonplay:getWidth() / 2
	buttony = height / 5
end

function love.update(dt)



end

function love.draw(dt)

	love.graphics.draw(buttons.buttonplay,
			   buttonx,
			   buttony)
	love.graphics.draw(buttons.buttonexit,
			   buttonx,
			   buttony * 3)

end

function love.mousepressed(x, y, dx, dy)

	if (x > buttonx and x < buttonx + buttons.buttonexit:getWidth()) and
           (y > (buttony * 3) and y < (buttony * 3) + buttons.buttonexit:getHeight()) then

		love.event.push('quit')

        end

	if (x > buttonx and x < buttonx + buttons.buttonplay:getWidth()) and
           (y > buttony and y < buttony + buttons.buttonplay:getHeight()) then

                pong.start()

        end
end

function pong.empty() end

function pong.start()
        -- Clear all callbacks
        love.load = pong.empty
        love.update = pong.empty
        love.draw = pong.empty
        love.keypressed = pong.empty
        love.keyreleased = pong.empty
        love.mousepressed = pong.empty
        love.mousereleased = pong.empty
				love.mousemoved = pong.empty

        love.filesystem.load("game.lua")()

        love.load()
end
