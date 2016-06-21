StdLib = {};

StdLib.base64_encode = function(str)
{
    return btoa(encodeURIComponent(str).replace(/%([0-9A-F]{2})/g, function(match, pl)
    {
        return String.fromCharCode("0x" + pl);
    }))
}

StdLib.base64_decode = function(str)
{
    return decodeURIComponent(Array.prototype.map.call(atob(str), function(c)
    {
        return "%" + ("00" + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(""));
}

StdLib.strToArrayBuffer = function(str)
{
    var bytes = new Uint8Array(str.length);
    for (var inx = 0; inx < str.length; inx++)
    {
        bytes[ inx ] = str.charCodeAt(inx);
    }

    return bytes;
}

StdLib.arrayBufferToStr = function(buffer)
{
    var str = "";
    for (var inx = 0; inx < buffer.byteLength; inx++)
    {
        str += String.fromCharCode(buffer[ inx ]);
    }

    return str;
}
