/*
 * Récupère l'adresse MAC de la carte réseau.
 */
public String getNetworkAdapterNo();

/*
 * Récupère le numéro de série du BIOS.
 */
public String getBIOSSerialNo();

/*
 * Récupère le numéro de série du disque physique.
 */
public String getDiskSerialNo();

/*
 * Indique si les informations hardware contenu dans le fichier 
 *   de licence correspond à celle de la machine hôte.
 */
public bool CompareHardwareID(String pathToLicenseFile);

/*
 * Indique si la période de validité de la licence est
 *  expiré.
 */
public bool isLicenceExpired(String pathToLicenseFile);

/*
 * Indique si oui ou non le fichier de licence désigner 
 *  par ce chemin est valide.
 */
public bool isLicenceValid(String pathToLicenseFile);

                // --- Outils --- // 
/*
 * Génère un hashé à partir d'une chaîne de caractères donné
 * en entrée, en utilisant l'algorithme SHA-256.
 */
public String hashWithSHA256(String s);

/*
 * Décode une chaîne de caractères encodée en base64.
 */
public String decodeBase64(String s);

