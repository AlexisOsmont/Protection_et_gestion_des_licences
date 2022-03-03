using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Text.Json;
using System.Text.Json.Serialization;
using System.Net;
using System.Net.Http;
using System.Net.NetworkInformation;
using System.Net.Http.Headers;

namespace ProjetAnnuel
{

    public static class RestApiConnector
    {
        private static HttpClient client = new HttpClient();
        private static String serverAddress = "192.168.0.1:8080"; //default server address but is overwritten in init method
        private static String serverProtocol = "https";

        static String locationReqGetSoftwareList = "/api/v1/Software/getSoftwareList";
        static String locationReqLicence = "/api/v1/Licence/requestLicence";
        public static async Task<List<RestAPIGetSoftwareList>> getSoftwareList()
        {
            List<RestAPIGetSoftwareList> result = null;
            String reqJSONResult = null;

            HttpResponseMessage response = await client.GetAsync(locationReqGetSoftwareList);
            if (response.IsSuccessStatusCode)
            {
                reqJSONResult = await response.Content.ReadAsStringAsync();

                result = new List<RestAPIGetSoftwareList>();

                JsonElement root = JsonDocument.Parse(reqJSONResult).RootElement;

                foreach (JsonElement item in root.EnumerateArray())
                {
                    RestAPIGetSoftwareList soft = item.Deserialize<RestAPIGetSoftwareList>();
                    result.Add(soft);
                }
            }

            return result;
        }

        public static async Task<String> requestLicence(RestAPIRequestLicence req)
        {
            String serializedJSONreq = JsonSerializer.Serialize<RestAPIRequestLicence>(req);
            String result = null;

            HttpResponseMessage response = await client.PostAsync(locationReqLicence, new StringContent(serializedJSONreq));
            if (response.IsSuccessStatusCode)
            {
                result = await response.Content.ReadAsStringAsync();
            }

            return result;
        }

        public static async Task<Boolean> pingServer()
        {
            bool pingable = false;
            Ping pinger = null;

            try
            {
                pinger = new Ping();
                PingReply reply = await pinger.SendPingAsync(serverAddress);
                pingable = reply.Status == IPStatus.Success;
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

            return pingable;
        }

        public static void init(String address, String port)
        {
            String uri = serverProtocol + "://" + address + ":" + port;
            client.BaseAddress = new Uri(uri);

            client.DefaultRequestHeaders.Accept.Clear();
            client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));

            serverAddress = address;
        }
    }
    public class RestAPIRequestLicence
    {
        [JsonPropertyName("UserMail")]
        public string UserMail { get; set; }

        [JsonPropertyName("UserPassword")]
        public String UserPassword { get; set; }

        [JsonPropertyName("SoftwareId")]
        public int SoftwareId { get; set; }

        [JsonPropertyName("HardwareHash")]
        public String HardwareHash { get; set; }
    }

    public class RestAPIGetSoftwareList
    {
        [JsonPropertyName("SoftwareId")]
        public int SoftwareId { get; set; }

        [JsonPropertyName("SoftwareName")]
        public String SoftwareName { get; set; }

        [JsonPropertyName("SoftwareDesc")]
        public String SoftwareDesc { get; set; }
    }
}
