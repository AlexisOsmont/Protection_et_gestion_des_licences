using HardwareHash;
public class Program
{
    static public void Main(string[] args)
    {
        if (args.Length == 0)
        {
            printUsage();
            return;
        }

        for (int i = 0; i < args.Length; i++)
        {
            if (args[i].ToLower().Equals("-h") || args[i].ToLower().Equals("--help"))
            {
                printUsage();
                return;
            }
        }

        if (args.Length != 5)
        {
            printUsage();
            return;
        }

        bool[] hh = { false, false, false, false, false };
        for (int i = 0; i < hh.Length; i++)
        {
            if (!Boolean.TryParse(args[i], out hh[i]))
            {
                Console.WriteLine("Erreur sur l'argument " + (i + 1) + ".");
                return;
            }
        }
        Console.WriteLine(MachineHardware.getHardwareId(hh[0], hh[1], hh[2], hh[3], hh[4]));
    }

    static private void printUsage()
    {
        Console.WriteLine("Usage : MachineHardware.exe [-h|--help] [hhmac hhboard hhdrive hhbios hhproc]");
        Console.WriteLine("\n\t-h, --help :\t\tAffiche l'aide ci présente.");
        Console.WriteLine("\n\thh... :\t\t\t\"true\" ou \"false\" indiquant si le programme doit prendre en compte " +
            "\n\t\t\t\tles interfaces réseaux, la carte mère, les disques, le bios, le processeur.");
        Console.WriteLine("");
    }
}