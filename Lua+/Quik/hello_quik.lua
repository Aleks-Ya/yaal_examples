is_run = true
function main()
	message("OnTrade: Hi", 1)
	error('wrong!')
	while is_run do
		sleep(1000)   -- приостановка (1000 = на 1 секунду)
		robot()
	end
end

function robot() --тело робота
	-- здесь ваш код
end --конец тела робота

function OnStop(stop_flag)
	is_run=false
	stop_flag=1
end