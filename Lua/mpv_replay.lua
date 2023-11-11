-- By Ctrl-1, Ctrl-2, etc replays previous N seconds with 50% speed
-- By Ctrl-ESC restore normal speed immediately
-- Install: ln -s ~/pr/home/yaal_examples/Lua/mpv_replay.lua ~/.config/mpv/scripts/mpv_replay.lua

function set_normal_speed()
    local normal_speed = 1.0
    mp.set_property_number("speed", normal_speed)
end

function replay(jump_seconds)
    local overlap_seconds = jump_seconds * 5
    local slow_speed = 0.5

    -- Jump backward
    mp.commandv("seek", -jump_seconds, "relative", "keyframes")

    -- Set slow speed
    mp.set_property_number("speed", slow_speed)

    -- Wait for the specified duration
    mp.add_timeout(jump_seconds + overlap_seconds, set_normal_speed)
end

for i = 1, 5 do
    f = function()
        replay(i)
    end
    mp.add_key_binding("Ctrl+" .. i, f)
end

mp.add_key_binding("Ctrl+ESC", set_normal_speed)
