//  C:\Windows\Microsoft.NET\Framework\v3.5\csc.exe /reference:"MachineHardware.dll" .\Main.cs
using System;

namespace Licence
{
    class App
    {
        static void Main(string[] args)
        {
            string hwid = HardwareID.ReturnHardwareID(true,true,true,true,true);
            Console.WriteLine(hwid);

            Console.ReadKey();
        }
    }
}