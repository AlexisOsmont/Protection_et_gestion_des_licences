namespace ProLogiciel;
using ProLicence;

public class ProLogiciel
{
    private const string SOFTWARE_NAME = "ProLogiciel";
    private const string LICENCE_FILE_PATH = @"..\..\..\prologiciel.license";

    static public void Main()
    {
        // Création d'une instance de LicenceChecker.
        LicenceChecker licenceChecker;
        try
        {
            licenceChecker = new LicenceChecker(LICENCE_FILE_PATH, SOFTWARE_NAME);
        }
        catch (Exception ex)
        {
            Console.WriteLine(ex.Message + "\n");
            return;
        }

        licenceChecker.setHardwareHashComposent(true, true, true, true, true);
        
        // Test de la licence.
        if (!licenceChecker.isValid())
        {
            Console.WriteLine("Integrité ou Validité de la licence non valide.\nRefus de lancer le logiciel.\n");
            exitConsole();
            return;
        }

        // La licence est valide. Le logiciel peut se lancer.
        Console.WriteLine("ProLogiciel peut se lancer à présent.\nHello World !\n");
        exitConsole();
        return;
    }

    static private void exitConsole()
    {
        Console.WriteLine("\n\n\nAppuyez sur une touche pour fermer la console...");
        Console.ReadKey();
    }
}