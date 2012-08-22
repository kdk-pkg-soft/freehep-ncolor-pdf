#
# Makefile for the Aida library
#
# Author: Mark Donszelmann
# Version: $Id: GNUmakefile 8612 2006-08-16 06:53:41Z duns $
#

ifneq ($(CERN_ROOT),)

#
# User Configuration: Define targets
#
OBJS = hbook fclose hnt_var2
DLLS = hbookAdapter
#
# On Solaris link with C++, otherwise sunmath.so is not referenced.
#
LINKNOCXX = true
LINKNOJAVA = true
LINKFORTRAN = true

DNAME = $(DLLS)-3.0.6-$(DST)$(TAREXT)
DDIR  = ../../../../LICENSE.txt
DDIR += ../../../../LGPL.txt
DINC =
DSLIB  = $(DST)/*$(DLLEXT) $(DST)/*$(LIBEXT)
endif   # CERN_ROOT

#
# No User Configuration below
#

include $(FREEHEP)/config/common.gmk
