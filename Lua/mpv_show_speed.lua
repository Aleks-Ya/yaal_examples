-- Shows current speed on the screen
-- Install: ln -s ~/pr/home/yaal_examples/Lua/mpv_show_speed.lua ~/.config/mpv/scripts/mpv_show_speed.lua
function update_speed()
    local speed = mp.get_property_number("speed")
    local formatted_speed = string.format("%.1f", speed)
    mp.osd_message("Speed: " .. formatted_speed, 0.1)
    mp.add_timeout(0.1, update_speed)
end

mp.register_event("file-loaded", update_speed)
