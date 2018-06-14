using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GestioTorneig.Model
{
    public class Modalitat
    {


        public Modalitat(int id, string descripcio)
        {
            Id = id;  
            Descripcio=descripcio;
        }

        public Modalitat()
        {
        }

        public int Id { get; set; }      

        public string Descripcio { get; set; }

       

    }
}
