Création de la DLL :

C:\Windows\Microsoft.NET\Framework\v3.5\csc.exe /target:library /out:MachineHardware.dll .\test.cs

Compilation du main avec la DLL : (Création de l'exécutable)

C:\Windows\Microsoft.NET\Framework\v3.5\csc.exe /reference:"MachineHardware.dll" .\Main.cs

Exécuter :

./Main.exe