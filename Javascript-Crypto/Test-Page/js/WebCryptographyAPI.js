WebCryptographyAPI = {};

WebCryptographyAPI.error = function(err)
{
    console.log("++> " + err);
}

WebCryptographyAPI.vector = function(size)
{
    var bytes = new Uint8Array(size);

    for (var inx = 0; inx < size; inx++)
    {
        bytes[ inx ] = 0;
    }

    return bytes;
}

WebCryptographyAPI.decrypt_data = function(key, data, call)
{
    var data   = StdLib.strToArrayBuffer(StdLib.base64_decode(data));
    var vector = WebCryptographyAPI.vector(16);
    var subtle = WebCryptographyAPI.subtle;

    var decrypt = subtle.decrypt(
        {
            name: "AES-CBC",
            iv: vector
        },
        key,
        data
    );

    var test = function(data)
    {
        encrypted_data = new Uint8Array(data);
        var dec_data = StdLib.base64_decode(StdLib.arrayBufferToStr(encrypted_data));

        // console.log("decrypt: " + dec_data);
        // document.body.innerHTML += "\n<h2>" + dec_data + "</h2>";
        call(dec_data);
    }

    decrypt.then(test);
    decrypt.catch(WebCryptographyAPI.error);
}


WebCryptographyAPI.encrypt_data = function(key, data, call)
{
    var str    = StdLib.base64_encode(data);
    var data   = StdLib.strToArrayBuffer(str);
    var vector = WebCryptographyAPI.vector(16);

    var subtle = WebCryptographyAPI.subtle;

    var encrypt = subtle.encrypt(
        {
            name: "AES-CBC",
            iv: vector
        },
        key,
        data
    );

    var manager = function(result)
    {
        encrypted_data = new Uint8Array(result);
        var enc_data = StdLib.base64_encode(StdLib.arrayBufferToStr(encrypted_data));

        // console.log("--> " + enc_data);
        // document.body.innerHTML += "<h2>" + enc_data + "</h2>";
        call(enc_data);
    }

    encrypt.then(manager);
    encrypt.catch(WebCryptographyAPI.error);
}

WebCryptographyAPI.conf = function()
{
    if (! window.crypto.webkitSubtle && ! window.crypto.subtle)
    {
        alert("Browser Error: supported browsers Firefox, Chrome or Safari");
        return;
    }

    // Safari
    if (window.crypto.webkitSubtle)
    {
        WebCryptographyAPI.subtle = window.crypto.webkitSubtle;
    }

    // Firefox & Chrome
    if (window.crypto.subtle)
    {
        WebCryptographyAPI.subtle = window.crypto.subtle;
    }
}

WebCryptographyAPI.genKey = function(passwd, call)
{
    var subtle = WebCryptographyAPI.subtle;
    var passwortArray = StdLib.strToArrayBuffer(passwd);

    var manager = function(result)
    {
        var importKey = subtle.importKey(
            "raw",
            result,
            {
                name: "AES-CBC"
            },
            false,
            ["encrypt", "decrypt"]
        );

        importKey.then(call);
        importKey.catch(WebCryptographyAPI.error);
    }

    var digest = subtle.digest(
        {
            name: "SHA-256"
        },
        passwortArray
    );

    digest.then(manager);
}

WebCryptographyAPI.encrypt = function(passwd, data, call)
{
    WebCryptographyAPI.conf();

    var encrypt = function(key)
    {
        WebCryptographyAPI.encrypt_data(key, data, call);
        WebCryptographyAPI.key = key;
    }

    WebCryptographyAPI.genKey(passwd, encrypt);
}

WebCryptographyAPI.decrypt = function(passwd, data, call)
{
    WebCryptographyAPI.conf();

    var decrypt = function(key)
    {
        WebCryptographyAPI.decrypt_data(key, data, call);
        WebCryptographyAPI.key = key;
    }

    WebCryptographyAPI.genKey(passwd, decrypt);
}

WebCryptographyAPI.log = function(result)
{
    console.log("--> " + result);
    document.body.innerHTML += "<h3>" + result + "</h3>";
}

WebCryptographyAPI.main = function()
{
    console.log("Start");

    var passwd      = "28051998PZ";
    var data        = "Hallo Welt!";
    var encryptData = "RltEayHDtCbCosO8ScKZO1vCj8K1wq5AKcO1w5BHwqlTFMKlwrHDoRgzw7VWwpw";

    document.body.innerHTML  = "<p>passwd: " + passwd + "</p>";
    document.body.innerHTML += "<p>data:   " + data   + "</p>";

    WebCryptographyAPI.encrypt(passwd, data, WebCryptographyAPI.log);
    WebCryptographyAPI.decrypt(passwd, encryptData, WebCryptographyAPI.log);
}

WebCryptographyAPI.main();
