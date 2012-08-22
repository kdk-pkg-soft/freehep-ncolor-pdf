#!/usr/local/bin/perl -w
# Copyright 2000, CERN, Geneva, Switzerland.
#*****************************************************************************************
# PDGPStoXML
#
# Converts the PostScript files of the PDG to an XML format readable by YaPPI
#
# Limitations:
#   - Tex formats sometimes incorrect
#   - Mass, Width and Decaymodes
#
# Part of YaPPI, Yet Another Particle Propterty Interface (yappi.freehep.org)
# 
# @author Mark Donszelmann (duns@www.cern.ch)
# @version $Id: PDGPStoXML.pl 8609 2006-08-15 22:36:23Z duns $
#*****************************************************************************************

sub encode {
    my($decimal, $font) = @_;
    
    my $text = "X";
    my $charName = "$font:$decimal";
    #                    print "$charName\n";
    if (exists $charTable{$charName}) {
        my $psChar = $charTable{$charName};
        if (exists $texTable{$psChar}) {
            $text = $texTable{$psChar};
        } else {
            my $ascii = chr($decimal);
            print "$file:                      Warning char not defined for '$fcode' '$charName' '$ascii' '$psChar'\n";                        
        }
    } else {
#        print "$file:                      Warning char not in table '$charName'\n";
        $text = chr($decimal);
    } 
    return $text;
}


# $name should include an initial "posx posy [ya]" to allow for tex encoding
sub toTexName {
    my ($name, $bold) = @_;
    my $rest;
    
    # take out tabs and newlines
    $name =~ tr/\n\r\t/ /;
    
    # FIXME: maybe a nicer pattern here...
#    $textPattern = '(.*?)' . '\(((?:\\\\\()?[\[\]a-zA-Z_0-9.!,\+ -]*(?:\\\\\))?|\S\d\d\d)\)';        # format $1 ($2)
    # matches: ...(x) where x = \(...\) or \(... or ...\) or ...\)... or ...
    my $textPattern = '(.*?)' . '\(' . '(\\\\\(\w+?\\\\\)|\\\\\(\w*?|\w*?\\\\\)|\w*?\\\\\).+?|.+?)' . '\)';
    my $coordPattern = '(-?\d+)\s+(-?\d+)\s+[ya]';
    
#    print "[", $name, "]\n";

    $rest = $name;
    my $texName = "";
    my $foundOffset = 0;
    my $yOffset = -12345;
    my $sup = 0;
    my $sub = 0;
    my $font = "/Unknown";

    my $mbox = 1;
    
    while ($rest =~ /$textPattern/) {
        $rest = $';
        my $prefix = $1;
        my $text = $2;
#        print "        '$text'\n";
        # check coordinate
        if ($prefix =~ /$coordPattern/) {
            $y = $2;
#            print "$yOffset ($y) $text \n"; 
            if ($foundOffset) {         
                if ($y - $yOffset > 40) {
                    # new line
                    if ($sup or $sub) {
                        $texName .= "}";
                        $sup = 0;
                        $sub = 0;
                    }
                    
                    $texName .= "\\;---\\;";           
                } elsif ($y - $yOffset > 6) {
                    if ($sup) {
                        $texName .= "}";
                        $sup = 0;
                    }
                    
                    $texName .= "_{";
                    $sub = 1;
                } elsif ($y - $yOffset < -6) {
                    if ($sub) {
                        $texName .= "}";
                        $sub = 0;
                    }
                    
                    $texName .= "^{";
                    $sup = 1;
                } else {
                    if ($sub or $sup ) {
                        $texName .= "}";
                        $sub = 0;
                        $sup = 0;
                    }                        
                }
            } else {
                $yOffset = $y;
                $foundOffset = 1;
            }             
        }
        
        # bar start
        $barPattern = '-?\d+\s+-?\d+\s+-?\d+\s+-?\d+\s+v\s+\d+\s+w';
        if ($prefix =~ /$barPattern/) {
            $texName .= "\\overline{";
        }
        
        
        # spacing ...
        if ($prefix =~ /^\s*[c|d|e|f|g|h|i|j|k|r]\s*/) {
            $texName .= "\\;";
        }
        
        # 25 b
        if ($prefix =~ /(-?\d+)\s+b/) {
            if ($1 > 0) { $texName .= "\\;"; }
        }

        # Fq
        if ($prefix =~ /(F\w)/) {
            $fcode =  $1;
            
            # check encoding
            if (exists $fontTable{$fcode}) {
                $font = $fontTable{$fcode};
            } else {
                print "$file:                                                 Warning fontName '$fcode' does not exist for '$text'\n";
            }            
        }

        $encodedText = "";
        for ($i=0; $i < length($text); $i++) {
            $char = substr($text, $i, 1);
#            print "$char\n";
            if ($char eq "\\") {
                $i++;
                $char = substr($text, $i, 1);
#            print "$char\n";                
                if ($char eq "(" or $char eq ")") {
                    $encodedText .= "\\" . $char;
#            print "$char\n";                
                } else {
                    $octal = "0" . substr($text, $i, 3);
#                    print "$octal\n";
                    $i += 2;
                    $decimal = oct($octal);
                    $encodedText .= encode($decimal, $font);
                }
            } else {
                $encodedText .= encode(ord($char), $font);
            }
        }
        $text = $encodedText;
                
#        if ($mbox) {
#            $texName .= "\\mbox{$text}";
#        } else {
            $texName .= $text;
#        }

        # bar end
        if ($prefix =~ /$barPattern/) {
            $texName .= "}";
        }
	}

    # close final super or sub if needed
    if ($sub or $sup ) {
        $texName .= "}";
        $sub = 0;
        $sup = 0;
    }                        


    # take out the \( and \)
    $texName =~ s/\\\(/\(/g;
    $texName =~ s/\\\)/\)/g;

    # take out the repeating pattern, if bold
    if ($bold) {
        while ($texName =~ /(.+)\1\1\1/) {
            $texName = "$`$1$'";
        }
    }

    # strip of any references
    $texName =~ s/\^\{\[.*?\]\}//;

    # strip ending \;(
    $texName =~ s/\\\;\($//;

    # strip ending (
    $texName =~ s/\($//;

#    print "[$texName]\n";
    
    return $texName;
}

sub toSpecialName {
    my($name) = @_;
    
    if ($name eq "e")       { $name = "e-"; }
    if ($name eq "p")       { $name = "p+"; }
    if ($name eq "mu")      { $name = "mu-"; }
    if ($name eq "W")       { $name = "W+"; }
    if ($name eq "tau")     { $name = "tau-"; }
    
    return $name;
}

sub toParticleTexName {
    my($name, $bold) = @_;
    
    my $texName = toTexName($name, $bold);
    my $antiTexName = "";
#    print "'$texName' \n";
    
    if ($texName =~ /\\pm/) {
        $antiTexName = $texName;
        $antiTexName =~ s/\\pm/-/g;
        $texName =~ s/\\pm/+/g;
    } else {
        if ($texName eq "e")                { $texName = "e^{-}";           $antiTexName = "e^{+}"; }
        elsif ($texName eq "p")             { $texName = "p^{+}";           $antiTexName = "p^{-}"; }
        elsif ($texName eq "\\mu")          { $texName = "\\mu^{+}}";       $antiTexName = "\\mu^{-}"; }
        elsif ($texName eq "\\mbox{W}")     { $texName = "\\mbox{W^{+}}";   $antiTexName = "\\mbox{W^{-}}"; }
        elsif ($texName eq "\\tau")         { $texName = "\\tau^{-}}";      $antiTexName = "\\tau^{+}"; }
        else                                { $antiTexName = "\\overline{$texName}"; }
    }
    
    return ($texName, $antiTexName);
}

sub toASCIIName {
    my ($texName) = @_;
    local ($name);
    
    $name = $texName;
    
    # take mbox{...} out
    $name =~ s/\\mbox{(.+?)}/$1/g;
    
    # cut off F(xx) \;X_{xx}
    $name =~ s/\\;[DFGHIPS]_\{[\d,]+\}//;
    
    # rename \; into space
    $name =~ s/\\;/ /g;
    
    # rename \overline{x} into xbar
    $name =~ s/\\overline{(.+?)}/$1bar/g;
    
    # rename \pm into +
    $name =~ s/\\pm/\+/g;
 
    # reverse super/sub
    $name =~ s/\^{(.+?)}_{(.+?)}/_{$2}\^{$1}/g;
 
    # switch sub and \prime 
#    $name =~ s/\\prime_{(.+?)}/_{$1}\\prime/g;
    
    # rename \ast into *
    $name =~ s/\\ast/\*/g;
    
    # rename \prime into '
    $name =~ s/\\prime/\'/g;
    
    # rename _{...} into (...)
    $name =~ s/_{(.+?)}/($1)/g;
    
    # remove \ ^{ and }
    $name =~ s/[}|\^{|\\]//g;
    
    # special cases (e.g e -> e-)
    $name = toSpecialName($name);
    
    # take out alias or older name
    $name =~ s/---\s*(was|or).+//;
        
    return $name;
}

sub doDecayLine {
    my ($line) = @_;
    local $fraction = "n/a";
    local $posError = "n/a";
    local $negError = "n/a";

    if ($line eq "") { return; }
    
    $dLines++;
    if ($line =~ /\(not\)[\w\s]+?\(seen\)/) {
        $fraction = "not seen";
    } elsif ($line =~ /\(seen\)/) {
        $fraction = "seen";
    } elsif ($line =~ /\(dominant\)/) {
        $fraction = "dominant";
    } elsif ($line =~ /\(la\)[\w\s]+?\(rge\)/) {
        $fraction = "large";
    } elsif ($line =~ /\(sea\)[\w\s]+?\(rched\)[\w\s]+?\(fo\)[\w\s]+?\(r\)/) {
        $fraction = "searched for";
    } elsif ($line =~ /\(\S031\)[\w\s]+?\(100\S045\)/) {
        $fraction = "~~ 100";
    } elsif ($line =~ /\(\S030\)[\w\s]+?\(100\)/) {
        $fraction = "~ 100";
    } elsif ($line =~ /$d2number/) {
        $fraction = "$1.$2";
        $posError = "$3.$4";
        $negError = "$5.$6";
    } elsif ($line =~ /$dnumber/) {
        $fraction = "$1.$2";
        $posError = "$3.$4";
        $negError = "$3.$4";
    } elsif ($line =~ /$psdouble/) {
        $fraction = "$1.$2";
    } elsif ($line =~ /$i2number/) {
        $fraction = "$1";
        $posError = "$2";
        $negError = "$3";
    } elsif ($line =~ /$inumber/) {
        $fraction = "$1";
        $posError = "$2";
        $negError = "$2";
    } elsif ($line =~ /$psint/) {
        $fraction = "$1";
    } elsif ($line =~ /Fp\(\{\)/) {
        # empty line, no number but dash at the end
        $fraction = "";
    } else {
        print "$file: Warning, cannot analyze decay line [$dLines] '$line'\n\n";
        return;
    }
    
    local $suffix=$';
    local $prefix=$`;
    local $power;
    local $type;
    
    # FIXME: we should still check for ranges!
    if ($suffix =~ /\(\S002\).+?\(10\).+?\(\S000\).+?\((\d+)\)/) {
        # x 10 - i
        $type = "rare";
        $power = "E-$1";
        $fraction = $fraction . "$power";
        $posError = ($posError ne "n/a") ? $posError . "$power" : $posError;
        $negError = ($negError ne "n/a") ? $negError . "$power" : $negError;
                
    } elsif ($suffix =~ /\(\S045\)/) {
        # %
        $type = "normal";
#        print " %\n";
    } else {
        $type = "limit";
#        print "\n$suffix\n";
    }
        
    # chop the <
    if ($prefix =~ /\(\<\)/) {
        $prefix = $`;
        $fraction = "&lt; " . $fraction;
        $type = "limit";
    }

    # chop the [x]
    if ($prefix =~ /\(\[\).*?\(\]\)/) {
        $prefix = $`;
    }

    # chop the LF
    if ($prefix =~ /\(LF\)/) {
        $prefix = $`;
    }

    # chop the C
    if ($prefix =~ /\(C\)/) {
        $prefix = $`;
    }

    # chop the B1
    if ($prefix =~ /\(B1\)/) {
        $prefix = $`;
    }

    # chop the L,B
    if ($prefix =~ /\(L\).+?\(,\).+?\(B\)/) {
        $prefix = $`;
    }

    # chop the P,CP
    if ($prefix =~ /\(P\).+?\(,\).+?\(CP\)/) {
        $prefix = $`;
    }

#    print "$prefix\n";
    local $decayTexName = toTexName($prefix, 0);
    local $decayName = toASCIIName($decayTexName);
    $decayName =~ s/ //g;
    
    local $decayText = "      <Decay name=\"$decayName\" texName=\"$decayTexName\" " . 
                         (($fraction ne "n/a") ? "fraction=\"$fraction\" " : "") .
                         (($posError ne "n/a") ? "posError=\"$posError\" " : "") .
                         (($negError ne "n/a") ? "negError=\"$negError\" " : "") .
                         "PUnit=\"MeV/c\"> " .
                         "\n" .
                         "      </Decay> \n";

    if ($type eq "normal") {
        $snDecay++;
        print DEC_NORMAL $decayText;
    } elsif ($type eq "rare") {
        print DEC_RARE $decayText;
        $srDecay++;
    } else {        
        print DEC_LIMIT $decayText;
        $slDecay++;
    }    
    print OUT "[$dLines] $line\n\n";
}

sub doDecayModes {
    my ($table) = @_;

    local $snDecay = 0;
    local $srDecay = 0;
    local $slDecay = 0;

    my $yOffset = -9999;
    my $keepParsing = 1; 
    my $dLines = 0;
    my $line = "";
    while (($keepParsing) and ($table ne "")) {
        if ($table =~ /^\s+/) {
            # whitespace IGNORED
            $table = $';
        } elsif ($table =~ /^\(P\)\s?\w\s?\(age\).+?\(\d\d\:\d\d\)\s*p/) {
            # (P) (age) (13) (Created:) (xx/xx/xxxx) (yy:yy) p  IGNORED
            $table = $';
            $line = "";
        } elsif ($table =~ /^\(Citation:\).+?\(http:\/\/p\)\s?\w\s?\(dg\.lbl\.gov\S\)\)(\s*p)?/) {
            # (Citation:)....(http://p)..(dg.lbl.gov\)) IGNORED
            $table = $';
            $line = "";
        } elsif ($table =~ /^\(HTTP:\/\/PDG\.LBL\.GO\)\s?\w\s?\(V\)/) {
            # (dg.lbl.gov\)) IGNORED
            $table = $';
            $line = "";
        } elsif ($table =~ /^bop/) {
            # bop IGNORED
            $table = $';
        } elsif ($table =~ /^eop/) {
            # eop IGNORED
            $table = $';
        } elsif ($table =~ /^%%Page:\s+\d+\s+\d+\s+\d+\s+\d+/) {
            # %%Page: 2 3 2 3 IGNORED
            $table = $';
            
            $yOffset = -9999;            
            doDecayLine($line);
            $line = "";            
        } elsif ($table =~ /^%%Trailer/) {
            # PostScript trailer, ignore rest of text
            $keepParsing = 0;
        } elsif ($table =~ /^(-?\d+)\s+(-?\d+)\s+(-?\d+)\s+(-?\d+)\s*[v]/) {
            # 25 27 29 10 v IGNORED
            $table = $';
        } elsif ($table =~ /^(-?\d+)\s+(-?\d+)\s*([ayV])/) {
            # 235 76 a | 235 76 y
            $table = $';
            $px = $1;
            $py = $2;
            $pc = $3;
#            print "$py ";
            if ($py > $yOffset + 25) {
                $yOffset = $py;
                if ($line =~ /^(-?\d+)\s+(-?\d+)\s*([ayV])\s*/) {
                    if ($' ne "") {
                        doDecayLine($line);
                    }
                }
                $line = "";
            }
            $line .= "$px $py $pc ";
            
        } elsif ($table =~ /^(-?\d+)\s*[bw]/) {
            # 26 b | 34 w
            $table = $';
            $line .= "$1 b ";
        } elsif ($table =~ /^(F[a-z])/) {
            # Fx
            $table = $';
            $line .= "$1 ";
        } elsif ($table =~ /^\(([\w:{`\/= \.\-\"\'\+\[\]\,]|\S\d\d\d|\S\(|\S\)|\\\\)+\)/) {
            # (text) | (\007) | (\() | (\))
            $table = $';
            $line .= "$& ";
        } elsif ($table =~ /^([cdefghijklopqrst])/) {
            # c | d | e | f | g | h | i | j | k | l | o | p | q | r | s | t
            $table = $';
            $line .= "$1 ";
        } else {
            # Parse Error
            $part = substr($table, 0, 132);
            print "\n$file: Error, cannot parse '$part...'\n\n";
            $keepParsing = 0;
        }
    }
        
    doDecayLine($line);
    $line = "";
    
    $nDecay += $snDecay;
    $rDecay += $srDecay;
    $lDecay += $slDecay;
    
    print "nDecay=$snDecay; rDecay=$srDecay; lDecay=$slDecay\n";
}


sub doParticle {
    my ($asciiName, $cName, $texName, $antiAsciiName, $antiTexName, 
        $massTable, $decayTable, $massPrefix, $widthPrefix, $pdgid, $charge) = @_;
    
    print FAM "       <ParticleRef name=\"$asciiName$cName\" /> \n";
    print PDG "   <ParticleType name=\"$asciiName$cName\" texName=\"$texName\" ";
    print PDG "antiName=\"$antiAsciiName$cName\" antiTexName=\"$antiTexName\" > \n";
    print DEC_NORMAL "   <ParticleType name=\"$asciiName$cName\" > \n";
    print DEC_RARE   "   <ParticleType name=\"$asciiName$cName\" > \n";
    print DEC_LIMIT  "   <ParticleType name=\"$asciiName$cName\" > \n";

    if ($pdgid) {
        print PDG "      <Data name=\"PDGID\" value=\"$pdgid\" /> \n";        
        print PDG "      <Data name=\"Charge\" value=\"$charge\" /> \n";
    }
         
	if ($massTable =~ /$massPrefix$massPattern1/) {
#    	print "$i   Mass = ", $1, ".", $2, " +- ", $3, ".", $4, " ", $5, " (S=", $6, ")", "\n";
        print PDG "      <Data name=\"Mass\"     texName=\"Mass \$m\$\" ";
        print PDG "value=\"$1.$2\" posError=\"$3.$4\" negError=\"$3.$4\" unit=\"$5\"/> \n";
        $nMass++;
    } elsif ($massTable =~ /$massPrefix$massPattern2/) {
#    	print "$i   Mass = ", $1, ".", $2, " + ", $3, ".", $4, " - ", $5, ".", $6, " ", $7, " (S=", $8, ")", "\n";
        print PDG "      <Data name=\"Mass\"     texName=\"Mass \$m\$\" ";
        print PDG "value=\"$1.$2\" posError=\"$3.$4\" negError=\"$5.$6\" unit=\"$7\"/> \n";
        $nMass++;
    } elsif ($massTable =~ /$massPrefix$massPattern3/) {
#    	print "$i   Mass = ", $1, " +- ", $2, " ", $3, " (S=", $4, ")", "\n";
        print PDG "      <Data name=\"Mass\"     texName=\"Mass \$m\$\" ";
        print PDG "value=\"$1\" posError=\"$2\" negError=\"$2\" unit=\"$3\"/> \n";
        $nMass++;
    } elsif ($massTable =~ /$massPrefix$massPattern4/) {
#    	print "$i   Mass = ", $1, " + ", $2, " - ", $3, " ", $4, " (S=", $5, ")", "\n";
        print PDG "      <Data name=\"Mass\"     texName=\"Mass \$m\$\" ";
        print PDG "value=\"$1\" posError=\"$2\" negError=\"$3\" unit=\"$4\"/> \n";
        $nMass++;
    } 

#    print "$massTable\n\n";

    if ($massTable =~ /$meanlifePattern1/) {
#	    print "   Meanlife = (", $1, ".", $2, " +- ", $3, ".", $4, ") x10^\\", $5, " ", $6, " ", $7, " (S=", $8, ")", "\n";
	    $val = "$1.$2E";
	    $poserror = "$3.$4E";
	    $negerror = "$3.$4E";
	    $power = $6;
	    $unit = $7;
	    if ($5 =~ "000") { $power = "-$power"; }
	    print PDG "      <Data name=\"LifeTime\" texName=\"Mean Life \$\\tau\$\" ";
	    print PDG "value=\"$val$power\" posError=\"$poserror$power\" negError=\"$negerror$power\" unit=\"$unit\"/> \n";      
        $nLife++;
	} elsif ($massTable =~ /$meanlifePattern2/) {
#	    print "   Meanlife = (", $1, ".", $2, " + ", $3, ".", $4, " - ", $5, ".", $6, ") x10^\\", $7, " ", $8, " ", $9, " (S=", $10, ")", "\n";
	    $val = "$1.$2E";
	    $poserror = "$3.$4E";
	    $negerror = "$5.$6E";
	    $power = $7;
	    $unit = $8;
	    if ($7 =~ "000") { $power = "-$power"; }
	    print PDG "      <Data name=\"LifeTime\" texName=\"Mean Life \$\\tau\$\" ";
	    print PDG "value=\"$val$power\" posError=\"$poserror$power\" negError=\"$negerror$power\" unit=\"$unit\"/> \n";      
        $nLife++;
	} 

    if ($massTable =~ /$widthPrefix$fullwidthPattern1a/) {
#        print "   Full Width = $1.$2 +- $3.$4 $5$6 \n";
        print PDG "      <Data name=\"Width\"    texName=\"Full Width \$\\Gamma\$\" ";
        print PDG "value=\"$1.$2\" posError=\"$3.$4\" negError=\"$3.$4\" unit=\"$5$6\"/> \n";
        $nWidth++;
    } elsif ($massTable =~ /$widthPrefix$fullwidthPattern1/) {
#        print "   Full Width = $1.$2 +- $3.$4 $5 \n";
        print PDG "      <Data name=\"Width\"    texName=\"Full Width \$\\Gamma\$\" ";
        print PDG "value=\"$1.$2\" posError=\"$3.$4\" negError=\"$3.$4\" unit=\"$5\"/> \n";
        $nWidth++;
    } elsif ($massTable =~ /$widthPrefix$fullwidthPattern2/) {
#        print "   Full Width = $1.$2 + $3.$4 - $5.$6 $7 \n";
        print PDG "      <Data name=\"Width\"    texName=\"Full Width \$\\Gamma\$\" ";
        print PDG "value=\"$1.$2\" posError=\"$3.$4\" negError=\"$5.$6\" unit=\"$7\"/> \n";
        $nWidth++;
    } elsif ($massTable =~ /$widthPrefix$fullwidthPattern3/) {
#        print "   Full Width = $1 +- $2 $3 \n";
        print PDG "      <Data name=\"Width\"    texName=\"Full Width \$\\Gamma\$\" ";
        print PDG "value=\"$1\" posError=\"$2\" negError=\"$2\" unit=\"$3\"/> \n";
        $nWidth++;
    } elsif ($massTable =~ /$widthPrefix$fullwidthPattern4/) {
#        print "   Full Width = $1 + $2 - $3 $4\n";
        print PDG "      <Data name=\"Width\"    texName=\"Full Width \$\\Gamma\$\" ";
        print PDG "value=\"$1\" posError=\"$2\" negError=\"$3\" unit=\"$4\"/> \n";
        $nWidth++;
    }
    
    if ($decayTable ne "") {
        doDecayModes($decayTable);
    }
    
    print PDG "   </ParticleType> \n";
    print DEC_NORMAL "   </ParticleType> \n";
    print DEC_RARE "   </ParticleType> \n";
    print DEC_LIMIT "   </ParticleType> \n";
}

sub getCharge {
    my ($name) = @_;
    
    my $charge = "";
    if ($pdgid) {
        $charge = $name;
        $charge =~ s/-/-1/;
        $charge =~ s/\+/\+1/;
        $charge =~ s/\+\+1/\+2/;
    }
    return $charge;
}

sub doMultiParticle {
    my ($name, $line) = @_;
    
    # take out tabs and newlines
    $line =~ tr/\n\t\r/ /;
    
    my ($texName, $antiTexName, $asciiName, $antiAsciiName);
    
    ($texName, $antiTexName) = toParticleTexName($name, 1);
    $asciiName = toASCIIName($texName);
    $antiAsciiName = toASCIIName($antiTexName);
    
    # remove all spaces from asciiname
    $asciiName =~ s/ //g;
    $antiAsciiName =~ s/ //g;
    
#    print "name      = ", $name, "\n";
#    print "texName     = ", $texName, "\n";
    print "ascName     = ", $asciiName, "\n";
#    print "antiTexName = ", $antiTexName, "\n";
#    print "antiAscName = ", $antiAsciiName, "\n";
    
#    print "Particle ($i): ", $name, "\n"; # = ", $1, " ", $2, " ", $3, "\n";

    my (@idList, @chargeList);
    my ($pdgid, $c);
    if (exists $PDGids{$asciiName}) {
        ($pdgid, $c) = split /:/, $PDGids{$asciiName};
        @idList = split /\s+/, $pdgid;
        @chargeList = split /,/, $c;
    } else {
        @idList = ("0");
        @chargeList = ("");
    }

    my $massTable = $line;
    my $decayTable = "";
    
    if ($line =~ /$decaylinePattern/) {
        $massTable = $`;
        $decayTable = $';
    }
#    } else {
#        print "$file, Warning cannot find decaymode table for particle: $asciiName\n";
#    }
    
#    print "$massTable\n\n";
    

    # there are two cases here, the book defines 
    #   - one entry, one mass or no mass, in which case
    #       - the mcfile may still contain multiple charges for which we write multiple particles
    #       - the mcfile contains a single charge, we write one particle
    #   - one entry, multiple masses in which case
    #       - the mcfile MUST contain multiple entries, and we write multiple entries
    #
    # in either case the mcfile may have NO entry, in which case we ignore the charge.

    my $cName;
    my $charge;
    # order: plusminus, plusplus, plus, minus, null, normal
    my @massFindPattern     = ('\(\S006\)[\s\w]+?\(mass\)', '\(\+\+\)[\s\w]+?\(mass\)', '\(\+\)[\s\w]+?\(mass\)', 
                               '\(\S000\)[\s\w]+?\(mass\)', '\(0\)[\s\w]+?\(mass\)');
    my @massCharge          = ("+", "++", "+", "-", "0");
    my @massPrefix          = ('\(\S006\)[\s\w]+?', '\(\+\+\)[\s\w]+?', '\(\+\)[\s\w]+?', 
                               '\(\S000\)[\s\w]+?', '\(0\)[\s\w]+?');
    my @widthPrefix         = ('\(\S006\)[\s\w]+?\(full\)[\s\w]+?', '\(\+\+\)[\s\w]+?\(full\)[\s\w]+?', '\(\+\)[\s\w]+?\(full\)[\s\w]+?', 
                               '\(\S000\)[\s\w]+?\(full\)[\s\w]+?', '\(0\)[\s\w]+?\(full\)[\s\w]+?');

    if (($massTable =~ /$massFindPattern[0]/) or 
        ($massTable =~ /$massFindPattern[1]/) or
        ($massTable =~ /$massFindPattern[2]/) or
        ($massTable =~ /$massFindPattern[3]/) or
        ($massTable =~ /$massFindPattern[4]/)
        ) {
        # loop over multi-masses

        my $k;
        for ($k=0; $k<@massFindPattern; $k++) {
 
            if ($massTable =~ /$massFindPattern[$k]/) {
                        
                $cName = $massCharge[$k];
                $charge = getCharge($cName);
                doParticle($asciiName, $cName, $texName, $antiAsciiName, $antiTexName,
                           $massTable, $decayTable, $massPrefix[$k], $widthPrefix[$k], $pdgid, $charge);
                $nParticle++;
                           
                # remove the entry
                delete $PDGids{"$asciiName$cName"};

            } # massFindPattern
        } # loop over patterns           
    } else {
        # single entry, one mass or no mass at all

        my $j = 0;
        foreach $pdgid (@idList) {

            if (@idList == 1) {
                $cName = "";
            } else {
                $cName = $chargeList[$j];
            }
            $charge = getCharge($chargeList[$j]);
            doParticle($asciiName, $cName, $texName, $antiAsciiName, $antiTexName,
                       $massTable, $decayTable, "", "", $pdgid, $charge);
                       
            $nParticle++;
            # remove the entry
            delete $PDGids{"$asciiName"};
            
            $j++;
        }        
    }
}

sub doLoadCharTables {
    my($text) = @_;
    local($table);

    # load tables 
# /FontName /CMSS10 def 
    while ($text =~ /\/FontName\s+(\/\w+)\s+def/) {
        $fontName = $1;
        $text = $';
        
#        print "$fontName\n";
        
        # find end
        # readonly def /FontBBox
        if ($text =~ /readonly\s+def\s+\/FontBBox/) {
            $table = $`;
            $text = $';
        } else {
            die "$file: Fatal: cannot find end of encoding table\n"; 
        }
        
# dup 31 /Oslash put
        while ($table =~ /dup\s+(\d+)\s+(\/\w+)\s+put/) {
            $code = $1;
            $charTable{"$fontName:$code"} = $2;            # format /CMMI10:31 = /circle
            $table = $';
#            print "$fontName:$code -> $2 \n";
        }
    }

    # load table pointers
    while ($text =~ /\/(F[a-z])\s+.+?(\/CM\w+)\s+/s) {
#        print "$1 -> $2\n";
        $fontTable{$1} = $2;
        $text = $';
    }   
}

sub doFile {
    my ($file) = @_;
    
    open PS, "<$file" or die "Cannot open input file: $!\n";

    my $line;
    read PS, $line, 1000000;
     
    $line =~ /$particlePattern/s;
    my $prefix = $`;
    my $rest = $';
    my $psParticleName = "$3 $4 y $5";
    
    doLoadCharTables($prefix);
    
    #
    # find family
    #
    if ($prefix =~ /$familyPattern/s) {
        my $familyTexName = toTexName($1, 1);
        my $familyName = toASCIIName($familyTexName);
#        print "  TexFamily: $familyTexName \n";
        print "ASCIIFamily: $familyName \n";
        print FAM "   <Family name=\"$familyName\" texName=\"$familyTexName\" > \n";
    } else {
        print "$file: Warning, could not extract family name, using 'undefined' \n";
        print FAM "   <Family name=\"Undefined\" texName=\"Undefined\" > \n";
    }        
    
    local $nParticle = 0;
    local $nMass = 0;
    local $nLife = 0;
    local $nWidth = 0;
    local $nDecay = 0;
    local $rDecay = 0;
    local $lDecay = 0;
    while ($rest =~ /$particlePattern/s) {
        $prefix = $`;        
        $rest = $';
        my $nextParticleName = "$3 $4 y $5";

        doMultiParticle($psParticleName, $prefix);
	    $psParticleName = $nextParticleName;
	}

    # Last particle
    doMultiParticle($psParticleName, $rest);
    
    print FAM "   </Family> \n";
    
    if (exists $familyRef{$file}) {
        my $reference = $familyRef{$file};
        ($rParticle, $rMass, $rLife, $rWidth, $rnDecay, $rrDecay, $rlDecay) = split /\s+/, $reference;
        if ($nParticle != $rParticle) {
            print "$file: Warning, found $nParticle particles, should have been $rParticle\n";
        }
        if ($nMass != $rMass) {
            print "$file: Warning, found $nMass masses , should have been $rMass\n";
        }
        if ($nLife != $rLife) {
            print "$file: Warning, found $nLife lifetimes , should have been $rLife\n";
        }
        if ($nWidth != $rWidth) {
            print "$file: Warning, found $nWidth widths, should have been $rWidth\n";
        }
        if ($nDecay != $rnDecay) {
            print "$file: Warning, found $nDecay normal decay modes , should have been $rnDecay\n";
        }
        if ($rDecay != $rrDecay) {
            print "$file: Warning, found $rDecay rare decay modes , should have been $rrDecay\n";
        }
        if ($lDecay != $rlDecay) {
            print "$file: Warning, found $lDecay limit decay modes , should have been $rlDecay\n";
        }
        
    } else {
        print "$file: no reference entry exists for this family\n";
    }
    
    $tParticle += $nParticle;
    $tMass += $nMass;
    $tLife += $nLife;
    $tWidth += $nWidth;
    $tnDecay += $nDecay;
    $trDecay += $rDecay;
    $tlDecay += $lDecay;
   
    close(PS);
}


sub doIDfile() {
#    my $mcFile = "garren_98a.mc";
    my $mcFile = "mass_width_00.mc";
    my $mcExpected = ($mcFile eq "mass_width_00.mc") ? 165 : 159;
    open(ID, $mcFile) or die "Cannot open input file: $!\n";

    my $mcParticles = 0;
    while (my $line = <ID>) {

# garren_98a.mc
#M     211                          1.3956995E-01  +3.5E-07-3.5E-07 pi            +
#W     211                          2.5284E-17     +5.0E-21-5.0E-21 pi            +

# mass_width_00.mc
#M     211                          1.3957018E-01  +3.5E-07 -3.5E-07 pi                  +
#W     211                          2.5284E-17     +5.0E-21 -5.0E-21 pi                  +

        my $errorPattern = '[+-]\d\.\dE[+-]\d\d';
        my $doublePattern = '\d\.\d*E[+-]\d\d';
        my $namePattern = '([a-zA-Z0-9()\'*/]+)';
        my $chargePattern = '([-0+,]+)';
        my $pattern = '^M\s+((\d+\s+){1,4})' . $doublePattern . '\s+' . 
                   $errorPattern . '\s*' . $errorPattern . '\s+' . $namePattern . '\s+' . $chargePattern;       # format $1 $3 $4
        if ($line =~ /$pattern/) {
            $name = $3;
            $pdgid = $1;
            $c = $4;
#            print "$name\n";
            #
            # Entries are stored by name if:
            #  - they contain multiple charges
            #  - they have charge 0, except if there exists an entry already for a plus particle
            #
            # Others are stored by name and charge
            #
#                print "      $pdgid $c[$j]\n";
#            if (exists $PDGids{"$name+"}) {
#                $PDGids{"$name$c"} = "$pdgid:$c";                
#            } els
            
            if ($c eq "-" or $c eq "+" or $c eq "++") {
                # single charge, plus or minus
                $name .= $c;
            } elsif ( # 0 charge, specified as xxx0 in book
                     $name eq "pi" or
                     $name eq "B" or
                     $name eq "B(s)" or    
                     $name eq "D" or      
                     $name eq "D*(2007)" or         
                     $name eq "D(1)(2420)" or
                     $name eq "D(2)*(2460)" or 
                     $name eq "K" or
                     $name eq "K*(892)" or
                     $name eq "K(2)*(1430)" or
                     $name eq "K(L)" or 
                     $name eq "K(S)" or 
                     $name eq "Lambda(b)" or
                     $name eq "Omega(c)" or
                     $name eq "Sigma" or
                     $name eq "Sigma(1385)" or
                     $name eq "Sigma(c)(2455)" or
                     $name eq "Xi(1530)" or
                     $name eq "Xi(c)" or
                     $name eq "Xi(c)'" or
                     $name eq "Xi"
                    ) {
                $name .= $c;
            } 
            
            if (exists $PDGids{$name}) {
                print "$mcFile: Warning MC files defines '$name' more than once\n";
            } else {
                $PDGids{$name} = "$pdgid:$c";
            }
            
            $mcParticles++;
            
        }
    }
    
    if ($mcParticles ne $mcExpected) {
        print "$mcFile: Warning processed $mcParticles particles, should be $mcExpected.\n";
    }
    
    close(ID);
}

sub checkIDfile {
    $i = 0;
    $s = "";
    foreach $key (sort keys %PDGids) {
        $s .= $key . " ";
        $i++;
    }
    if ($i) {
         print "Warning, cannot store PDGID for particles: $s\n";
         print "$i PDGIDs were not stored.\n";
    }
}

sub doFamilyRefFile {
    open(FREF, "<family.ref") or die "Cannot open input file: $!\n";
    
    while ($line = <FREF>) {
        if ($line =~ /^([A-Za-z0-9.]+)\s+/) {
            $familyRef{$1}=$';
        }
    }
    
    close(FREF);
}


#
# Main program
#

$tParticle = 0;
$tMass = 0;
$tLife = 0;
$tWidth = 0;
$tnDecay = 0;
$trDecay = 0;
$tlDecay = 0;


$xmlHeader = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?> \n" . 
             "<!--=============================================================================--> \n" .
             "<!-- Copyright of file, 2000, CERN, Geneva, Switzerland. (yappi.freehep.org)     --> \n" .
             "<!-- Copyright of data, 2000, PDG. (pdg.lbl.gov)                                 --> \n" .
             "<!--=============================================================================--> \n" .
             "<!-- ATTENTION: this file is GENERATED. To override values, use a different file --> \n" .
             "<!--=============================================================================--> \n" .
             "<PPML source=\"PDG-2000-Generated\" xmlns:ppml=\"http://www.w3.org/1999/XMLSchema-instance\" \n" .
             "      ppml:nonamespaceSchemaLocation='ppml.xsd'> \n";
                        
open FAM, ">PDG-Family.xml" or die "Cannot open output file: $!\n";
open PDG, ">PDG-Properties.xml" or die "Cannot open output file: $!\n";
open DEC_NORMAL, ">PDG-NormalDecayChannels.xml" or die "Cannot open output file: $!\n";
open DEC_RARE,   ">PDG-RareDecayChannels.xml" or die "Cannot open output file: $!\n";
open DEC_LIMIT,  ">PDG-LimitDecayChannels.xml" or die "Cannot open output file: $!\n";

print FAM $xmlHeader;
print PDG $xmlHeader;
print DEC_NORMAL $xmlHeader;
print DEC_RARE $xmlHeader;
print DEC_LIMIT $xmlHeader;

    %fontTable = ();
    %charTable = ();
    %texTable = (
         "/Delta",           "\\Delta", 
         "/Gamma",           "\\Gamma", 
         "/Lambda",          "\\Lambda", 
         "/Omega",           "\\Omega", 
         "/Sigma",           "\\Sigma", 
         "/Theta",           "\\Theta", 
         "/Upsilon",         "\\Upsilon", 
         "/Xi",              "\\Xi", 

         "/chi",            "\\chi",
         "/gamma",          "\\gamma",
         "/eta",            "\\eta",
         "/lscript",        "\\ell",
         "/phi",            "\\phi",
         "/mu",             "\\mu",
         "/nu",             "\\nu",
         "/omega",          "\\omega", 
         "/pi",             "\\pi",
         "/psi",            "\\psi", 
         "/rho",            "\\rho", 
         "/sigma",          "\\sigma", 
         "/tau",            "\\tau",

        "/approxequal",     "\\approx", 
        "/arrowright",      "\\arrowright", 
        "/asteriskmath",    "\\ast", 
        "/bracketleft",     "[",
        "/bracketright",    "]",
        "/comma",           ",",
        "/emdash",          "---",
        "/endash",          "--",
        "/equal",           "=",
        "/greaterequal",    "\\geq",
        "/hyphen",          "-",
        "/minus",           "-", 
        "/minusplus",       "\\mp", 
        "/multiply",        "\\times",
        "/period",          ".", 
        "/plus",            "+", 
        "/plusminus",       "\\pm", 
        "/prime",           "\\prime",
        "/slash",           "/",
        "/zerooldstyle",    "0",

        "/zero",            "0",
        "/one",             "1",
        "/two",             "2",
        "/three",           "3",
        "/four",            "4",
        "/five",            "5",
        "/six",             "6",
        "/seven",           "7",
        "/eight",           "8",
        "/nine",            "9",
                );
    
    for($i='a'; $i le 'z'; $i++) {
        $texTable{"/$i"} = "$i";
    }

    for($i='A'; $i le 'Z'; $i++) {
        $texTable{"/$i"} = "$i";
    }

    $titlebox = '0\s+\-?\d+\s+\-?\d+\s+\-?\d+\s+v\s+' .
                '0\s+\-?\d+\s+\-?\d+\s+\-?\d+\s+v\s+' .
                '\-?\d+\s+\-?\d+\s+\-?\d+\s+\-?\d+\s+v\s+' .
                '\-?\d+\s+\-?\d+\s+\-?\d+\s+\-?\d+\s+v\s+' .
                '\-?\d+\s+\-?\d+\s+a\s+';
    $box    = '0\s+\-?\d+\s+(\-?\d+)\s+\-?\d+\s+v\s+' .
              '0\s+(\-?\d+)\s+\-?\d+\s+\-?\d+\s+v\s+' .
              '(\-?\d+)\s+(\-?\d+)\s+a\s+';   # format x,y $3, $4
    $schar  = '\(\S(\d+)\)';                                    # pi, tau, -            format $1   
    $int    = '(\d+)';                                          # 80                    format $1   
    $psint  = '\((?:\S\()?' . $int . '(?:\S\))?\)';             # (80) or ((80))                    
    $double = '(\d+)\)[\w\s]+?\(:\)[\w\s]+?\((\d+)';            # 1.2                   format $1.$2
    $psdouble = '\((?:\S\()?' . $double . '(?:\S\))?\)';        # (1.2) or ((1.2))                  
    $string = '\(([a-zA-Z_0-9.]+)(?:\S\))?\)';                  # text or text)         format $1   
    $derror  = '[\w\s]+?\(\S006\)[\w\s]+?' . $psdouble;         # +- 0.00035            format $1.$2
    $dperror  = '[\w\s]+?\(\+\)[\w\s]+?' . $psdouble;           # + 0.0003          format $1.$2
    $dnerror = '[\w\s]+?\(\S000\)[\w\s]+?' .  $psdouble;        # - 0.00035         format $1.$2
    $ierror  = '[\w\s]+?\(\S006\)[\w\s]+?' . $psint;            # +- 10                 format $1
    $iperror  = '[\w\s]+?\(\+\)[\w\s]+?' . $psint;              # + 10              format $1
    $inerror  = '[\w\s]+?\(\S000\)[\w\s]+?' . $psint;           # - 10              format $1
    $dnumber = $psdouble . '[\w\s]+?' . $derror;                # 1.2 +- 0.005          format $1.$2 +- $3.$4
    $d2number = $psdouble . $dperror . $dnerror . '(?:[\w\s]+?\(\S\)\))?'; # 1.2 + 0.005 - 0.002   format $1.$2 + $3.$4 - $5.$6
    $inumber = $psint . '[\w\s]+?' . $ierror;                   # 80 +- 10              format $1 + $2
    $i2number = $psint . $iperror . $inerror . '(?:[\w\s]+?\(\S\)\))?';    # 80 + 10 - 9           format $1 + $2 - $3
    $scale  = '[\w\s]+?\(\S\(S\)[\w\s]+?\(=\)[\w\s]+?' . $string;     # (S = 1.2)             format $1

    $particlePattern = $box . '(.+?)' . '\s+\-?\d+\s+\2\s+V\s+0\s+\-?\d+\s+\1\s+\-?\d+\s+v';                            # format $5
    
    $familyPattern = $titlebox . '(.+?)' . '\-?\d+\s+\-?\d+\s*y';            # format $1

    $massPattern1 = '\([M|m]ass\)(?:\w|\d+\s*\w)\s*F\w\(m\)\w\s*F\w\(=\)\w' .       # Mass m = 
                   $dnumber . '\w' . $string .                                      # 139.57018 +- 0.00035 Mev 
                   '(?:' . $scale . ')?';                                           # (S = 1.2) 
    
    $massPattern2 = '\([M|m]ass\)(?:\w|\d+\s*\w)\s*F\w\(m\)\w\s*F\w\(=\)\w' .       # Mass m = 
                   $d2number . '\d+\s+\d+\s*\w\s*F\w' . $string .                   # 139.57018 + 0.00035 - 0.00029 Mev 
                   '(?:' . $scale . ')?';                                           # (S = 1.2) 
    
    $massPattern3 = '\([M|m]ass\)(?:\w|\d+\s*\w)\s*F\w\(m\)\w\s*F\w\(=\)\w' .       # Mass m = 
                   $inumber . '\w' . $string .                                      # 139 +- 10 Mev 
                   '(?:' . $scale . ')?';                                           # (S = 1.2) 
    
    $massPattern4 = '\([M|m]ass\)(?:\w|\d+\s*\w)\s*F\w\(m\)\w\s*F\w\(=\)\w' .       # Mass m = 
                   $i2number . '\d+\s+\d+\s*\w\s*F\w' . $string .                   # 139 + 10 - 9 Mev 
                   '(?:' . $scale . ')?';                                           # (S = 1.2) 
    
    $meanlifePattern1 = '\(life\)[\w\s]+?' .                                        # Mean life
                        '\(\S+\)[\w\s]+?\(=\)[\w\s]+?' .                            # t =
                        $dnumber . '[\w\s]+?' .                                     # (2.6033 +- 0.0005)
                        '\(\S\d+\)[\w\s]+?\(10\)[\w\s]+?' .                         # x 10
                        $schar . '[\w\s]+?' . $string . '[\w\s]+?' .                # -8
                        $string .                                                   # s
                        '(?:' . $scale . ')?';                                      # (S = 1.2)                     

    $meanlifePattern2 = '\(life\)[\w\s]+?' .                                        # Mean life
                        '\(\S+\)[\w\s]+?\(=\)[\w\s]+?' .                            # t =
                        $d2number . '[\w\s]+?' .                                    # (2.6033 + 0.0005 - 0.0003)
                        '\(\S\d+\)[\w\s]+?\(10\)[\w\s]+?' .                         # x 10
                        $schar . '[\w\s]+?' . $string . '[\w\s]+?' .                # -8
                        $string .                                                   # s
                        '(?:' . $scale . ')?';                                      # (S = 1.2)                     

    $fullwidthPattern1a = '\(width\)[\w\s]+?\(\S000\)[\w\s]+?\(=\)[\w\s]+?' .       # width T =
                         $dnumber . '[\w\s]+?' .                                    # 2.6033 +- 0.0005
                         $string . '[\w\s]+?' . $string;                            # k eV

#(width)g (\000)h(=)f(150)p Fm(:)p Fo(2)g Fl(\006)f Fo(0)p Fm(:)p Fo(8)i(MeV)
#(width)g(\000) h(=)f(4)p Fm(:)p Fo(458)g Fl(\006)f Fo(0)p Fm(:)p Fo(032)h(MeV)
    $fullwidthPattern1 = '\(width\)[\w\s]+?\(\S000\)[\w\s]+?\(=\)[\w\s]+?' .         # width T =
                         $dnumber . '[\w\s]+?' .                                     # 2.6033 +- 0.0005
                         $string;                                                    # MeV

#(width)g(\000)h(=)f(185)p Fm(:)p Fo(1)686 1289 y Fj(+)5 b(3)p Ff(:)p Fj(4)686 1328 y Fk(\000)g Fj(2)p Ff(:)p Fj(6)797 1311 y Fo(MeV)
    $fullwidthPattern2 = '\(width\)[\w\s]+?\(\S000\)[\w\s]+?\(=\)[\w\s]+?' .        # width T =
                         $d2number . '[\w\s]+?' . $string;                          # 2.6033 + 0.0005 - 0.0002 MeV
                         

#(width)g(\000)h (=)f(149)g Fl(\006)g Fo(40)g(MeV)
#(width)g(\000)h(=)f(202)g Fl(\006)g Fo(60)g(MeV)
#(width)g(\000)h(=)f(161)g Fl(\006)g Fo(10)g(MeV)
#(width)g(\000)h(=)f(161) g Fl(\006)g Fo(10)g(MeV)
    $fullwidthPattern3 = '\(width\)[\w\s]+?\(\S000\)[\w\s]+?\(=\)[\w\s]+?' .        # width T =
                         $inumber . '[\w\s]+?' .                                    # 20 +- 1
                         $string;                                                   # MeV

    $fullwidthPattern4 = '\(width\)[\w\s]+?\(\S000\)[\w\s]+?\(=\)[\w\s]+?' .        # width T =
                         $i2number . '[\w\s]+?' . $string;                          # 20 + 1 - 2 MeV

    $decaylinePattern = '0\s+\d+\s+1620\s+4\s+v';

                                                  


%PDGids = ();
doIDfile();

%familyRef = ();
doFamilyRefFile();

foreach $file (@ARGV) {
    $outfile = $file . "\.txt";
    open OUT, ">$outfile" or die "Cannot open output file: $!\n";           
    doFile($file);
    close(OUT);    
}

print DEC_NORMAL "</PPML> \n";
print DEC_RARE "</PPML> \n";
print DEC_LIMIT "</PPML> \n";
print PDG "</PPML> \n";
print FAM "</PPML> \n";

close(DEC_LIMIT);
close(DEC_RARE);
close(DEC_NORMAL);
close(PDG);
close(FAM);


checkIDfile();

print "Total: processed $tParticle particles, $tMass masses, $tLife lifetimes, $tWidth widths, " . 
      "$tnDecay normal decay modes, $trDecay rare decay modes, $tlDecay limit decay modes.\n";
