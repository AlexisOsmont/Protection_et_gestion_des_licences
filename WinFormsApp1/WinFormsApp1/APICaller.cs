using System.Text.Json;
using System.Net.NetworkInformation;
using System.Net.Http.Headers;
using System.Text.Json.Serialization;

namespace ActivationSoftware
{
    internal class APICaller
    {
        private static readonly string PROTOCOL = "https";
        private static readonly string SERVER = "srv-dpi-proj-gestlic-web.univ-rouen.fr";
        private static readonly int PORT = 8443;

        private static readonly string API_PATH = "/api/v1";
        private static readonly string GET_SOFT_LIST = API_PATH + "/Software/getSoftwareList";
        private static readonly string GET_LICENSE = API_PATH + "/Licence/requestLicence";

        private readonly HttpClient client;
        private readonly string serverUrl;

        public APICaller()
        {
            // Construct the server URL
            serverUrl = PROTOCOL + "://" + SERVER + ":" + PORT;

            // TEMPORARY -> DANGEROUS
            // Modify handler to trust our self-signed (and all) certificates
            HttpClientHandler handler = new HttpClientHandler();
            handler.ClientCertificateOptions = ClientCertificateOption.Manual;
            handler.ServerCertificateCustomValidationCallback =
                (httpRequestMessage, cert, cetChain, policyErrors) =>
                {
                    return true;
                };

            // create a new HttpClient and set parameters
            client = new HttpClient(handler)
            {
                BaseAddress = new Uri(serverUrl)
            };
            // to accept JSON content response
            client.DefaultRequestHeaders.Accept.Add(
                new MediaTypeWithQualityHeaderValue("application/json")
            );
        }

        // Turn into synchronous function because unknown errors
        // with the asynchronous method
        public List<APISoftware> GetSoftwareList()
        {
            List<APISoftware>? result = null;
            string strResponse;

            // try to execute the request to API
            HttpResponseMessage response = client.GetAsync(
                    serverUrl + GET_SOFT_LIST
            ).Result;
            // check response status code : 200 -> OK
            if (response.IsSuccessStatusCode)
            {
                strResponse = response.Content.ReadAsStringAsync().Result;
                result = new List<APISoftware>();

                // Parse the response
                JsonElement root = JsonDocument.Parse(strResponse).RootElement;

                foreach (JsonElement item in root.EnumerateArray())
                {
                    APISoftware? soft = item.Deserialize<APISoftware>();
                    result.Add(soft);
                }
            }

            return result;
        }

        // Turn into synchronous function because unknown errors
        // with the asynchronous method
        public string RequestLicence(APIRequestLicence req)
        {
            // The API expect a JSON array, so encapsulted req
            APIRequestLicence[] array = new APIRequestLicence[1];
            array[0] = req;

            // Serialize the JSON request to pass it into the body of the request
            string reqParams = JsonSerializer.Serialize<APIRequestLicence[]>(array);
            string? result = null;

            // Try to execute the POST request
            HttpResponseMessage response = client.PostAsync(serverUrl + GET_LICENSE,
                new StringContent(reqParams)).Result;

            // Check response status code
            if (response.IsSuccessStatusCode)
            {
                result = response.Content.ReadAsStringAsync().Result;
            }
            else
            {
                // send an assertion with the status code
                throw new HttpRequestException("Bad response", null, response.StatusCode);
            }
            return result;
        }

        // Send a ping request to the server to test if we can connect to it
        public static bool PingServer()
        {
            bool available = false;
            Ping? pinger = null;

            try
            {
                pinger = new Ping();
                // synchronous way to avoid problems
                PingReply reply = pinger.Send(SERVER, 1000);
                available = (reply.Status == IPStatus.Success);
            }
            catch (PingException)
            {
                // Discard PingExceptions and return false;
            }
            finally
            {
                if (pinger != null)
                {
                    pinger.Dispose();
                }
            }

            return available;
        }
    }

    // JSON object for the GET_LICENSE request
    public class APIRequestLicence
    {
        [JsonPropertyName("UserMail")]
        public string UserMail { get; set; }

        [JsonPropertyName("UserPassword")]
        public string UserPassword { get; set; }

        [JsonPropertyName("SoftwareId")]
        public int SoftwareId { get; set; }

        [JsonPropertyName("HardwareHash")]
        public string HardwareHash { get; set; }
    }

    // JSON object for the GET_SOFT_LIST request
    public class APISoftware
    {
        [JsonPropertyName("SoftwareId")]
        public int SoftwareId { get; set; }

        [JsonPropertyName("SoftwareName")]
        public string SoftwareName { get; set; }

        [JsonPropertyName("SoftwareDesc")]
        public string SoftwareDesc { get; set; }

        public override string ToString()
        {
            return SoftwareName;
        }
    }
}
