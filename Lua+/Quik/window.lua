is_run = true
function main()
	t_id = AllocTable()
	AddColumn(t_id, 1, "Код", true, QTABLE_STRING_TYPE, 20)
	t = CreateWindow(t_id)
	SetWindowCaption(t_id, "Опционы") 
	closed = IsWindowClosed(t_id)
	--message('closed'.. tostring(closed), 1)
	assert(not(closed))
	local rows, columns = GetTableSize (t_id)
	InsertRow(t_id, rows) 
	local row = 1
	SetCell(t_id, 1, row, 'null name of instrument') -- "Бумага"
	ColourRowInGreen(row)
end

function ColourRowInRed(num_row)
  SetColor(t_id, num_row, QTABLE_NO_INDEX, RGB(255,150,150), RGB(0,0,0), RGB(255,150,150), RGB(0,0,0))
end
function ColourRowInYellow(num_row)
  SetColor(t_id, num_row, QTABLE_NO_INDEX, RGB(255,255,200), RGB(0,0,0), RGB(255,255,200), RGB(0,0,0))
end
function ColourRowInGreen(num_row)
  SetColor(t_id, num_row, QTABLE_NO_INDEX, RGB(150,255,150), RGB(0,0,0), RGB(150,255,150), RGB(0,0,0))
end

function robot() --тело робота
	-- здесь ваш код
end --конец тела робота

function OnStop(stop_flag)
	is_run=false
	stop_flag=1
end