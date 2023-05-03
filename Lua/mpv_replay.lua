-- By Ctrl-Shift-Left replays previous 3 seconds with 50% speed
-- Install: ln -s ~/pr/home/yaal_examples/Lua/mpv_replay.lua ~/.config/mpv/scripts/mpv_replay.lua
function custom_replay()
    local jump_seconds = 3
    local overlap_seconds = jump_seconds * 5
    local slow_speed = 0.5
    local normal_speed = 1.0

    -- Jump backward
    mp.commandv("seek", -jump_seconds, "relative", "keyframes")

    -- Set slow speed
    mp.set_property_number("speed", slow_speed)

    -- Wait for the specified duration
    mp.add_timeout(jump_seconds + overlap_seconds, function()
        -- Set normal speed
        mp.set_property_number("speed", normal_speed)
    end)
end

-- Bind the function to a key combination, e.g., Ctrl+Shift+Left
mp.add_key_binding("Ctrl+Shift+LEFT", "custom_replay", custom_replay)
