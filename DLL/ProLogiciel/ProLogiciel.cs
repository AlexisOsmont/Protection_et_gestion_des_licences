namespace ProLogiciel;
using ProLicence;

public class ProLogiciel
{
    private const string LICENCE_FILE_PATH = @"..\..\..\files\licence.txt";

    static public void Main()
    {
        // Création d'une instance de LicenceChecker.
        LicenceChecker licenceChecker;
        try
        {
            licenceChecker = new LicenceChecker(LICENCE_FILE_PATH);
        }
        catch (Exception ex)
        {
            Console.WriteLine(ex.Message + "\n");
            return;
        }
        
        // Test de la licence.
        if (!licenceChecker.isValid())
        {
            Console.WriteLine("Integrité ou Validité de la licence non valide.\nRefus de lancer le logiciel.\n");
            return;
        }

        // La licence est valide. Le logiciel peut se lancer.
        Console.WriteLine("ProLogiciel peut se lancer à présent.\nHello World !\n");
        return;
    }
}