using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using System.Text;
using System.Threading.Tasks;
using Windows.Storage.Streams;
using Windows.UI.Xaml.Media.Imaging;

namespace GestioTorneig.Model
{
    public class Soci
    {
        public Soci(int id,
            string nif, 
            string nom,
            string cognom1,
            string cognom2,
            DateTime dataAlta,
            string passwordHash,
            byte[] imatge,
            int estadisticamodalitat )
          //  EstadisticaModalitat estadisticamodalitat )
        {
            Id = id;
            Nif = nif;
            Nom = nom;
            Cognom1 = cognom1;
            Cognom2 = cognom2;
            DatAlta = dataAlta;
            PasswordHash = passwordHash;
           // Foto = imatge;
            FullName = Nom + " " + cognom1 + (cognom2 == null ? "" : " " + cognom2);
            Estadisticamodalitat = estadisticamodalitat;
            //EstadisticaModalitat = estadisticamodalitat;




        }

        public Soci(int id,
             string nif,
             string nom,
             string cognom1,
             string cognom2,
             DateTime dataAlta,
             string passwordHash,
           EstadisticaModalitat estadisticamodalitat)
        { }

        public Soci(int numero, string nif, string nom, string cognom1, string cognom2, DateTime dataAlta, string passwordHash, int estadistica)
        {
            this.numero = numero;
            Nif = nif;
            Nom = nom;
            Cognom1 = cognom1;
            Cognom2 = cognom2;
            this.dataAlta = dataAlta;
            PasswordHash = passwordHash;
            this.estadistica = estadistica;
        }

        private byte[] mFoto;
        private int numero;
        private DateTime dataAlta;
        private int estadistica;

        public byte[] Foto
        {
            get { return mFoto; }
            set
            {
                mFoto = value;
                setImage();
            }
        }

        public bool Inscrit { get; set; }


        public BitmapImage BitmapFoto { get; set; }


        private async void setImage()
        {
            BitmapFoto = await ImageFromBytes(Foto);
        }

        public async static Task<BitmapImage> ImageFromBytes(byte[] bytes)
        {
            BitmapImage image = new BitmapImage();
            using (InMemoryRandomAccessStream stream = new InMemoryRandomAccessStream())
            {
                await stream.WriteAsync(bytes.AsBuffer());
                stream.Seek(0);
                await image.SetSourceAsync(stream);
            }
            return image;
        }












     //  public EstadisticaModalitat EstadisticaModalitat { get; set; }
        public int Estadisticamodalitat { get; set; }

        public string FullName { get; set; }

        public int Id { get; set; }
        
        public string Nif { get; set; }

        public string Nom { get; set; }

        public string Cognom1 { get; set; }
        public string Cognom2 { get; set; }

        public DateTime DatAlta { get; set; }

        public string PasswordHash { get; set; }



    }
}
