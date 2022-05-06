using HardwareHash;

namespace ActivationSoftware
{
    // The controller class simplifly interactions between the
    // desktop app and the APICaller (~ MVC)
    internal class Controller
    {
        // Test connection with server
        public static bool TestConnection()
        {
            return APICaller.PingServer();
        }

        // Return the software list
        public static List<APISoftware> FetchSoftwareList()
        {
            APICaller api = new();
            return api.GetSoftwareList();
        }

        // Request the license
        public static string RequestLicence(string userMail,
                string userPassword, int softwareId, Dictionary<string, bool> components)
        {
            APIRequestLicence req = new()
            {
                UserMail = userMail,
                UserPassword = userPassword,
                SoftwareId = softwareId,
                HardwareHash = MachineHardware.getHardwareId(components)
            };

            APICaller caller = new();
            return caller.RequestLicence(req);
        }
    }
}
