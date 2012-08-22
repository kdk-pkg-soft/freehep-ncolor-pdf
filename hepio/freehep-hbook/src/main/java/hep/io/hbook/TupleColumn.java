package hep.io.hbook;

/** A base class for all tuple columns */
public abstract class TupleColumn extends HbookObject
{
   TupleColumn(CompositeHbookObject parent, String name)
   {
      super(parent, name);
   }
   int type;
}


