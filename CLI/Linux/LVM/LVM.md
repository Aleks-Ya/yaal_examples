# LVM CLI

## Abbreviations
LV = "Logical Volume"
PV = "Physical Volume"
VG = "Volume Group"

## Commands

### Physical Volume
List PVs (table): `sudo pvs`
List PVs (detailed): `sudo pvdisplay`
Show PV details: `sudo pvdisplay /dev/loop19`
Delete a PV: `sudo pvremove /dev/loop20`

### Volume Groups
List VGs (table): `sudo vgs`
List VGs (detailed): `sudo vgdisplay`
List VGs (detailed, include LVs and PVs): `sudo vgdisplay -v`
Show VG details: `sudo vgdisplay -v vgubuntu`
Delete a VG: `sudo vgremove myvg`

### Logical Volume
List LVs (table): `sudo lvs`
List LVs (detailed): `sudo lvdisplay`
Delete a LV: `lvremove`
