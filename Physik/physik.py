#!/usr/bin/env python
# -*- coding: utf-8 -*-

def main(element):
    mp = 1.007276
    mn = 1.008655

    atomeKern = round(element["MasseKern"])
    protonen  = element["Protonen"]
    neutronen = atomeKern - protonen

    mKern = element["MasseKern"]

    mNukleonen = (protonen * mp) + (neutronen * mn)

    deltaM = mNukleonen - mKern

    print element["Name"]
    print "∆m = " + str(deltaM) + "u"

    # uToMeV = 931.494

    # print str(deltaM * uToMeV) + " MeV"

    c = 299792458
    u = 1.660539 * 10 ** -27

    jouleToMeV = 6241506479960

    # print "∆E = " + str(deltaM * u * c * c) + " j"
    # print "∆E = " + '{0:.50f}'.format(deltaM * u * c * c) + " j"
    print "∆E = " + str(deltaM * u * c * c * jouleToMeV) + " MeV"
    print


main({
    "Name": "Nickel",
    "MasseKern": 61.912985,
    "Protonen": 28
})

main({
    "Name": "Sauerstoff",
    "MasseKern": 15.990526,
    "Protonen": 8
})

main({
    "Name": "Uran",
    "MasseKern": 235,
    "Protonen": 92
})

main({
    "Name": "Barium",
    "MasseKern": 139,
    "Protonen": 56
})

main({
    "Name": "Krypton",
    "MasseKern": 95,
    "Protonen": 36
})


# main({
#     "Name": "Wasserstoff",
#     "MasseKern": 1.008,
#     "Protonen": 1
# })
