echo off
echo NUL>_.class&&del /s /f /q *.class
cls
javac com/krzem/image_editor/Main.java&&java com/krzem/image_editor/Main img.png
start /min cmd /c "echo NUL>_.class&&del /s /f /q *.class"