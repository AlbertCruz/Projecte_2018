using MySql.Data.MySqlClient;
using GestioTorneig.Model;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.Storage;
using Windows.Storage.FileProperties;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Media.Imaging;

namespace GestioTorneig.BD
{
    public class BDConnector
    {
        #region props
        public MySqlConnection Connection { get; set; }

        public string ConnectionString { get; set; }

        private ObservableCollection<Soci> inscrits;
        private ObservableCollection<Modalitat> modalitats;

        //public ObservableCollection<SociList> Clients
        //{
        //    get
        //    {
        //        if (clients == null)
        //        {
        //            clients = new ObservableCollection<SociList>();
        //            // clients = getLlistaClients();
        //        }
        //        return clients;
        //    }
        //    set { clients = value; }
        //}


        //public ObservableCollection<Modalitat> Modalitats
        //{
        //    get
        //    {
        //        if (modalitats == null)
        //        {
        //            modalitats = new ObservableCollection<Modalitat>();
        //         modalitats = getLlistaModalitat();
        //        }
        //        return modalitats;
        //    }
        //    set { modalitats = value; }
        //}

        public string modalitatbyId(int idModalitat)
        {

            string desc = null;
            try
            {
                EncodingProvider provider = CodePagesEncodingProvider.Instance;
                Encoding.RegisterProvider(provider);
                using (Connection = new MySqlConnection(ConnectionString))
                {
                    Connection.Open();
                    MySqlCommand command = Connection.CreateCommand();
                    command.CommandText = "select desc_modalitat from modalitat  where id = @idModalitat";
                    command.Parameters.Add(new MySqlParameter("idModalitat", idModalitat));
                    using (MySqlDataReader reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            desc = reader.GetString("desc_modalitat");
                        }
                    }
                }
            }
            catch (MySqlException e)
            {
                Debug.WriteLine("ERROR BD modalitatbyId  " + e.Message);
            }

            return desc;
        }



        public int insertTorneo(int id, string nom, DateTime data_inici, Modalitat modalitat, Object preinscripcion)
        {
            try
            {
                EncodingProvider provider = CodePagesEncodingProvider.Instance;
                Encoding.RegisterProvider(provider);
                Debug.WriteLine(data_inici.Date.ToString("yyyy-MM-dd"));
                string data = data_inici.Date.ToString("yyyy-MM-dd");



                using (Connection = new MySqlConnection(ConnectionString))
                {

                    Connection.Open();
                    MySqlCommand command = Connection.CreateCommand();
                    command.CommandText = "insert into torneig (id,nom,data_inici,modalitat_id,preinscripcio_open,data_final) values(@id,@nom,@data_inici,@id_modalitat, @preinscricion,@data_closed,1)";
                    command.Parameters.Add(new MySqlParameter("id", id));
                    command.Parameters.Add(new MySqlParameter("nom", nom));
                    command.Parameters.Add(new MySqlParameter("data_inici", data));
                    command.Parameters.Add(new MySqlParameter("id_modalitat", modalitat.Id));
                    //command.Parameters.Add(new MySqlParameter("id_inscrit", null));
                    command.Parameters.Add(new MySqlParameter("preinscricion", true));
                    command.Parameters.Add(new MySqlParameter("data_closed", null));
                    command.ExecuteNonQuery();
                }

            }
            catch (MySqlException e)
            {
                Debug.WriteLine("ERROR BD INSERT TORNEO:  " + e.Message);
            }
            return id;
        }





        public ObservableCollection<Torneo> getLlistaTorneos()
        {
            ObservableCollection<Torneo> torneos = new ObservableCollection<Torneo>();

            try
            {
                EncodingProvider provider = CodePagesEncodingProvider.Instance;
                Encoding.RegisterProvider(provider);
                using (Connection = new MySqlConnection(ConnectionString))
                {
                    Connection.Open();
                    MySqlCommand command = Connection.CreateCommand();
                    command.CommandText = "select  * from torneig ";
                    //order by id_modalitat";
                    using (MySqlDataReader reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {

                            int numero = reader.GetInt16("id");
                            string nom = reader.GetString("nom");
                            DateTime dataIni = Convert.ToDateTime(reader["data_inici"]);
                            string num2;
                            int idModalitat = 0;

                            if (reader.IsDBNull(reader.GetInt16("modalitat_id")))
                            {
                                num2 = null;
                            }
                            else
                            {
                                num2 = reader.GetString(reader.GetOrdinal("modalitat_id"));
                                idModalitat = Int16.Parse(num2);
                            }
                            // int idModalitat = reader.GetInt16("id_modalitat");                          
                            string num;
                            //if (reader.IsDBNull(reader.GetInt16("id_inscrit")))
                            //{
                            //    num = null;
                            //}
                            //else
                            //{
                            //    num = reader.GetString(reader.GetOrdinal("id_inscrit"));
                            //    int idInscrit = Int16.Parse(num);
                            //}
                            Boolean inscr = reader.GetBoolean("preinscripcio_open");
                            Debug.WriteLine("Cognom: " + nom);
                            Modalitat modActual = new Modalitat(idModalitat, modalitatbyId(idModalitat));
                            torneos.Add(new Torneo(numero, nom, dataIni, modActual, inscr));
                        }
                    }
                }
            }


            catch (MySqlException e)
            {
                Debug.WriteLine("ERROR BD GetLlistaTorneo  " + e.Message);
            }
            return torneos;
        }



        #endregion props

        public BDConnector(string connectionString)
        {
            this.ConnectionString = connectionString;
        }



        public ObservableCollection<Socilist> getLlistaSocis()
        {
            ObservableCollection<Socilist> socislist = new ObservableCollection<Socilist>();

            try
            {
                EncodingProvider provider = CodePagesEncodingProvider.Instance;
                Encoding.RegisterProvider(provider);
                using (Connection = new MySqlConnection(ConnectionString))
                {

                    Connection.Open();
                    MySqlCommand command = Connection.CreateCommand();
                    command.CommandText = "select * from soci";

                    using (MySqlDataReader reader = command.ExecuteReader())
                    {

                        while (reader.Read())
                        {

                            int numero = reader.GetInt32("id");
                            string nif = reader.GetString("nif");
                            string nom = reader.GetString("nom");
                            DateTime dataAlta = Convert.ToDateTime(reader["data_alta"]);
                            string cognom1 = reader.GetString("cognom1");
                            string cognom2 = reader[5] as string;
                            string passwordHash = reader.GetString("password");
                            // int estadistica = reader.GetInt16("estadistica_modalitat");
                            //EstadisticaModalitat em = new EstadisticaModalitat(estadistica,getEstadisticaByID(estadistica));
                            Socilist s = new Socilist(numero, nif, nom, cognom1, cognom2, -1);
                            socislist.Add(s);
                        }
                    }
                    return socislist;
                }
            }
            catch (MySqlException e)
            {
                Debug.WriteLine("ERROR BD GetLlistaClients:  " + e.Message);
            }
            return socislist;
        }



        public int existentegrupo(int id_soci)
        {
            int numero = 0;

            try
            {
                EncodingProvider provider = CodePagesEncodingProvider.Instance;
                Encoding.RegisterProvider(provider);


                using (Connection = new MySqlConnection(ConnectionString))
                {

                    Connection.Open();
                    MySqlCommand command = Connection.CreateCommand();
                    command.CommandText = "select count(grup_id) as numero from inscrit where  soci_id= @id_Soci";
                    command.Parameters.Add(new MySqlParameter("id_soci", id_soci));
                    using (MySqlDataReader reader = command.ExecuteReader())
                    {

                        while (reader.Read())
                        {
                            numero = reader.GetInt16("numero");
                        }
                    }
                }
            }
            catch (MySqlException e)
            {
                Debug.WriteLine("ERROR BD existentegrup:  " + e.Message);
            }

            return numero;
        }

        public int getIdGrup()
        {
            int index = 0;
            try
            {
                EncodingProvider provider = CodePagesEncodingProvider.Instance;
                Encoding.RegisterProvider(provider);
                using (Connection = new MySqlConnection(ConnectionString))
                {

                    Connection.Open();
                    MySqlCommand command = Connection.CreateCommand();
                    command.CommandText = "select max(comptador) from comptadors where upper(clau) = 'GRUP'";
                    using (MySqlDataReader reader = command.ExecuteReader())
                    {
                        if (reader.Read())
                        {
                            index = reader.GetInt16("max(comptador)") + 1;
                        }
                    }
                }
            }
            catch (MySqlException e)
            {
                Debug.WriteLine("ERROR GET ID GRUP SEGUENT BD:  " + e.Message);
            }
            ActualitzarComptadorsGrup(index);
            return index;
        }



        public void AddInscrit(int numero, int grup, int torneo, DateTime dataInscrp)
        {
            try
            {
                EncodingProvider provider = CodePagesEncodingProvider.Instance;
                Encoding.RegisterProvider(provider);
                using (Connection = new MySqlConnection(ConnectionString))
                {
                    Connection.Open();
                    MySqlCommand command = Connection.CreateCommand();
                    command.CommandText = "insert into inscrit (soci_id,grup_id,torneig_id,data_creacio) values(@id_soci,@id_grup, @id_torneo,@data_inscrp)";
                    command.Parameters.Add(new MySqlParameter("id_soci", numero));
                    command.Parameters.Add(new MySqlParameter("id_torneo", torneo));
                    command.Parameters.Add(new MySqlParameter("id_grup", grup));
                    command.Parameters.Add(new MySqlParameter("data_inscrp", dataInscrp));
                    command.ExecuteNonQuery();
                }
            }
            catch (MySqlException e)
            {
                Debug.WriteLine("ERROR BD INSERT INSCRIT:  " + e.Message);
            }
        }


        public ObservableCollection<Ttancats> getTorneosTanctats()
        {
            ObservableCollection<Ttancats> cerrados = new ObservableCollection<Ttancats>();
            {
                try
                {
                    EncodingProvider provider = CodePagesEncodingProvider.Instance;
                    Encoding.RegisterProvider(provider);
                    using (Connection = new MySqlConnection(ConnectionString))
                    {

                        Connection.Open();
                        MySqlCommand command = Connection.CreateCommand();
                        command.CommandText = "SELECT g.id, g.descripcio, p.guanyador , p.soci_a, p.soci_b " +
                                            "FROM partida p " +
                                            "left JOIN grup g ON g.id = p.grup_id " +
                                            "LEFT JOIN torneig t ON t.id = g.torneig_id where t.preinscripcio_open = 0 group by g.id";

                        using (MySqlDataReader reader = command.ExecuteReader())
                        {
                            while (reader.Read())
                            {
                                int id = reader.GetInt32("id");
                                //object datat = reader["data_closed"];
                                //DateTime? data = (datat == null) ? (DateTime?)null : Convert.ToDateTime(datat);
                                string desc = reader.GetString("descripcio");
                                string enumeGanador = reader[3] as string;
                                //string cognom2 = reader[5] as string;
                                int iA = reader.GetInt32("soci_a");
                                int iB = reader.GetInt32("soci_b");
                                Ttancats s = new Ttancats(id, null, desc, Enums.GUANYADOR.A, iA, iB);
                                cerrados.Add(s);
                            }
                        }
                    }
                }
                catch (MySqlException e)
                {
                    Debug.WriteLine("ERROR EN EL LA LECTURA TORNEOS CERRRADOS " + e.Message);
                }
                return cerrados;
            }
        }



        public ObservableCollection<TorneoActiu> getTorneosActiu()
        {
            ObservableCollection<TorneoActiu> actius = new ObservableCollection<TorneoActiu>();
            try
            {
                EncodingProvider provider = CodePagesEncodingProvider.Instance;
                Encoding.RegisterProvider(provider);
                using (Connection = new MySqlConnection(ConnectionString))
                {
                    Connection.Open();
                    MySqlCommand command = Connection.CreateCommand();
                    command.CommandText = "select t.id, data_inici, count(g.id) as Qtgrup, count(p.id)  as Qtpartida " +
                                        "FROM torneig t  " +
                                        "LEFT JOIN grup g ON t.id = g.torneig_id " +
                                        "LEFT JOIN partida p ON g.id = p.grup_id GROUP BY t.id, p.id ";

                    using (MySqlDataReader reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            int id = reader.GetInt16("id");
                            DateTime dataIni = Convert.ToDateTime(reader["data_inici"]);
                            int qtygrup = reader.GetInt16("Qtgrup");
                            int qtypartida = reader.GetInt16("Qtpartida");
                            actius.Add(new TorneoActiu(id, dataIni, qtygrup, qtypartida));
                        }
                    }
                }
            }
            catch (MySqlException e)
            {
                Debug.WriteLine("ERROR BD getTorneosActiu:  " + e.Message);
            }
            return actius;
        }


        //TODO buscar a on es crida
        public void updateGrup(int id, int grup)
        {
            try
            {
                EncodingProvider provider = CodePagesEncodingProvider.Instance;
                Encoding.RegisterProvider(provider);
                using (Connection = new MySqlConnection(ConnectionString))
                {
                    Connection.Open();
                    MySqlCommand command = Connection.CreateCommand();
                    command.CommandText = "update grup set id_partida =@id where id=@grup";
                    command.Parameters.Add(new MySqlParameter("id", id));
                    command.Parameters.Add(new MySqlParameter("grup", grup));
                    command.ExecuteNonQuery();
                }
            }
            catch (MySqlException e)
            {
                Debug.WriteLine("ERROR BD UpdateGrup  " + e.Message);
            }
        }


        public void addPartida(Partida p, int id)
        {
            {
                try
                {
                    EncodingProvider provider = CodePagesEncodingProvider.Instance;
                    Encoding.RegisterProvider(provider);
                    using (Connection = new MySqlConnection(ConnectionString))
                    {
                        //string a = "PENDENT";
                        int a = 0;
                        Connection.Open();
                        MySqlCommand command = Connection.CreateCommand();
                        command.CommandText = "insert into partida (id,carambolesA,carambolesB,data_partida,soci_a,soci_b,num_entrades,estat_partida,guanyador,mode_victoria,grup_id) " +
                                            "values(@id,@carambolesA,@carambolesB,@data_partida,@inscrit_a,@inscrit_b,@num_entrades,@estat_partida,@guanyador,@motiu_victoria,@id_partida)";
                        command.Parameters.Add(new MySqlParameter("id", p.Id));
                        command.Parameters.Add(new MySqlParameter("carambolesA", p.CarambolesA));
                        command.Parameters.Add(new MySqlParameter("carambolesB", p.CarambolesB));
                        command.Parameters.Add(new MySqlParameter("data_partida", p.DataPartida));
                        command.Parameters.Add(new MySqlParameter("inscrit_a", p.InscritA.Numero));
                        command.Parameters.Add(new MySqlParameter("inscrit_b", p.InscritB.Numero));
                        command.Parameters.Add(new MySqlParameter("num_entrades", p.NumEntrades));
                        command.Parameters.Add(new MySqlParameter("estat_partida", a));
                        command.Parameters.Add(new MySqlParameter("guanyador", null));
                        command.Parameters.Add(new MySqlParameter("motiu_victoria", null));
                        command.Parameters.Add(new MySqlParameter("id_partida", id));
                        command.ExecuteNonQuery();
                    }
                }
                catch (MySqlException e)
                {
                    Debug.WriteLine("ERROR BD INSERT PARTIDA:  " + e.Message);
                }
            }
        }

        internal int getIdPartida()
        {

            int index = 0;
            try
            {
                EncodingProvider provider = CodePagesEncodingProvider.Instance;
                Encoding.RegisterProvider(provider);
                using (Connection = new MySqlConnection(ConnectionString))
                {
                    Connection.Open();
                    MySqlCommand command = Connection.CreateCommand();
                    command.CommandText = "select max(comptador) from comptadors where upper(clau) = 'PARTIDA'";
                    using (MySqlDataReader reader = command.ExecuteReader())
                    {
                        if (reader.Read())
                        {
                            index = reader.GetInt16("max(comptador)") + 1;
                        }
                    }
                }
            }
            catch (MySqlException e)
            {
                Debug.WriteLine("ERROR GET ID PARTIDA SEGUENT BD:  " + e.Message);
            }

            ActualitzarComptadorsPartida(index);

            return index;


        }

        private void ActualitzarComptadorsPartida(int index)
        {
            try
            {
                EncodingProvider provider = CodePagesEncodingProvider.Instance;
                Encoding.RegisterProvider(provider);
                using (Connection = new MySqlConnection(ConnectionString))
                {

                    Connection.Open();
                    MySqlCommand command = Connection.CreateCommand();
                    command.CommandText = "update comptadors set comptador = @index where upper(clau) = 'PARTIDA'";
                    command.Parameters.Add(new MySqlParameter("index", index));
                    command.ExecuteNonQuery();
                }
            }
            catch (MySqlException e)
            {
                Debug.WriteLine("ERROR BD actualitzarComptadorsTorneo  " + e.Message);
            }
        }

        public ObservableCollection<ActiuGrup> getgrupo(int id)
        {

            ObservableCollection<ActiuGrup> ActiuGp = new ObservableCollection<ActiuGrup>();
            {
                try
                {
                    EncodingProvider provider = CodePagesEncodingProvider.Instance;
                    Encoding.RegisterProvider(provider);
                    using (Connection = new MySqlConnection(ConnectionString))
                    {

                        Connection.Open();
                        MySqlCommand command = Connection.CreateCommand();
                        command.CommandText = @"select gr.torneig_id, gr.descripcio,
                                                (select count(grup_id)
                                                from partida p                                                
                                                where p.estat_partida = 0 and 
                                                torneig_id =@id) /count(p.id) as media
                                                from grup  gr
												LEFT JOIN partida p on p.grup_id = gr.id
                                                left join torneig t on t.id = gr.torneig_id
                                                where gr.torneig_id =@id and t.preinscripcio_open = 0
                                                group by gr.descripcio,gr.torneig_id";
                        command.Parameters.Add(new MySqlParameter("Id", id));
                        using (MySqlDataReader reader = command.ExecuteReader())
                        {
                            while (reader.Read())
                            {
                                int i = reader.GetInt32("torneig_id");
                                string desc = reader.GetString("descripcio");
                                string m = reader[2] as string;
                                int media = 0;
                                if (m != null)
                                {
                                    media = Int32.Parse(m);
                                }
                                ActiuGrup s = new ActiuGrup(i, desc, media);
                                ActiuGp.Add(s);
                            }
                        }
                    }
                }
                catch (MySqlException e)
                {
                    Debug.WriteLine("ERROR EN COSNSULTA GRUPO ACTIVO:  " + e.Message);
                }
                return ActiuGp;
            }

        }

        public ObservableCollection<Torneo> getTorneosFiltrados(string dataFrom, string dataTo, int act)
        {
            ObservableCollection<Torneo> filtro = new ObservableCollection<Torneo>();
            try
            {
                EncodingProvider provider = CodePagesEncodingProvider.Instance;
                Encoding.RegisterProvider(provider);
                using (Connection = new MySqlConnection(ConnectionString))
                {

                    Connection.Open();
                    MySqlCommand command = Connection.CreateCommand();
                    command.CommandText = " select * from torneig where((@dini = ' ' or @dfin = ' ') or data_ini>= @dini and data_ini<= @dfin))and(@act = -1 or preinscripcio_open = @act)";
                    command.Parameters.Add(new MySqlParameter("dini", dataFrom));
                    command.Parameters.Add(new MySqlParameter("dfin", dataTo));
                    command.Parameters.Add(new MySqlParameter("act", act));
                    using (MySqlDataReader reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            int numero = reader.GetInt16("id");
                            string nom = reader.GetString("nom");
                            DateTime dataIni = Convert.ToDateTime(reader["data_inici"]);
                            string num2;
                            int idModalitat = 0;
                            if (reader.IsDBNull(reader.GetInt16("modalitat_id")))
                            {
                                num2 = null;
                            }
                            else
                            {
                                num2 = reader.GetString(reader.GetOrdinal("modalitat_id"));
                                idModalitat = Int16.Parse(num2);
                            }
                            string num;
                            Boolean inscr = reader.GetBoolean("preinscripcio_open");
                            Modalitat modActual = new Modalitat(idModalitat, modalitatbyId(idModalitat));
                            filtro.Add(new Torneo(numero, nom, dataIni, modActual, inscr));
                        }
                    }
                }
            }
            catch (MySqlException e)
            {
                Debug.WriteLine("ERROR BD getTorneosFiltrados:" + e.Message);
            }
            return filtro;
        }




        public ObservableCollection<Grup> getgruposinfiltro(Torneo act)
        {
            ObservableCollection<Grup> lgrupo = new ObservableCollection<Grup>();
            try
            {
                EncodingProvider provider = CodePagesEncodingProvider.Instance;
                Encoding.RegisterProvider(provider);
                using (Connection = new MySqlConnection(ConnectionString))
                {
                    Connection.Open();
                    MySqlCommand command = Connection.CreateCommand();
                    command.CommandText = @"select g.* from grup g
											left join torneig t on g.torneig_id = t.id where t.id=@act ";
                    command.Parameters.Add(new MySqlParameter("act", act.Id));

                    using (MySqlDataReader reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            int id = reader.GetInt16("id");
                            String descripcio = reader.GetString("descripcio");
                            int torneoid = reader.GetInt16("torneig_id");
                            int caramboles = reader.GetInt16("caramboles_victoria");
                            int limit = reader.GetInt16("entrades_limit");
                            //int mismo = reader.GetInt16("mismo_grupo");
                            lgrupo.Add(new Grup(id, act, descripcio, caramboles, limit, torneoid));
                        }
                    }
                }
            }
            catch (MySqlException e)
            {
                Debug.WriteLine("ERROR BD getTorneosActiu:  " + e.Message);
            }
            return lgrupo;
        }



        public ObservableCollection<ClassInscrit> inserClassInscrit(int id)
        {
            ObservableCollection<ClassInscrit> posiciones = new ObservableCollection<ClassInscrit>();
            try
            {
                EncodingProvider provider = CodePagesEncodingProvider.Instance;
                Encoding.RegisterProvider(provider);
                using (Connection = new MySqlConnection(ConnectionString))
                {
                    Connection.Open();
                    MySqlCommand command = Connection.CreateCommand();
                    command.CommandText = @"select x.posicion as posicion,x.idgrupo as idGrupo,x.descrip as des,x.soci as soci,
                                          x.partidas as partidas,x.ganadas as ganadas,x.perdidas as perdidas,x.caramboles/x.partidas as coeficiente
                                          from
                                          (SELECT  @rownum:= @rownum + 1 as posicion, 
                                          g.id as idgrupo,
                                          g.descripcio as descrip,
                                          s.nom as soci,
                                          (select count(id)from partida p where (p.soci_a = i.soci_id or p.soci_b = i.soci_id)and p.estat_partida = 1 ) as partidas,
                                          (select count(id)from partida p where p.guanyador = i.soci_id and p.estat_partida = 1  ) as ganadas,
                                          (select count(id)from partida p where p.guanyador <> i.soci_id  and p.estat_partida = 1 ) as perdidas,
                                          CASE i.soci_id 
                                          WHEN p.soci_a = i.soci_id 
                                          THEN p.carambolesA ELSE p.carambolesB END as caramboles 
                                          FROM(SELECT @rownum:= 0) r,
                                                 grup g 
												 left join  inscrit i on i.grup_id = @grup_Id
                                                 left join soci s on s.id = i.soci_id 
												 left join partida p on g.id = p.grup_id
                                          )x order by coeficiente asc";
                    using (MySqlDataReader reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            int numero = reader.GetInt32("posicion");
                            int idGrupo = reader.GetInt32("idGrupo");
                            string desc = reader.GetString("des");
                            string soci = reader.GetString("soci");
                            int partidas = reader.GetInt32("partidas");
                            int ganadas = reader.GetInt32("ganadas");
                            int perdidas = reader.GetInt32("perdidas");
                            int coeficiente = reader.GetInt32("coeficiente");
                            ClassInscrit s = new ClassInscrit(numero, desc, partidas, ganadas, perdidas, coeficiente);
                            posiciones.Add(s);

                        }
                    }
                }

            }
            catch (MySqlException e)
            {
                Debug.WriteLine("ERROR BD EN RECUPERAR DATOS CLASSIFICACIO:  " + e.Message);
            }
            return posiciones;

        }

        public void AddGroup(int id, int idTorneig, string descripcio, int carambolesVistoria, int limitEntrades, int mismogrupo, int idPartida)
        {
            //  int numInst = getInsertIdInscrit();
            int p = 0;
            try
            {
                EncodingProvider provider = CodePagesEncodingProvider.Instance;
                Encoding.RegisterProvider(provider);
                using (Connection = new MySqlConnection(ConnectionString))
                {

                    Connection.Open();
                    MySqlCommand command = Connection.CreateCommand();
                    command.CommandText = @"insert into grup (id,descripcio,torneig_id,caramboles_victoria,entrades_limit,) values(@id,@descripcio,@id_torneig,@caramboles_vistoria,@limit_entrades)";
                    command.Parameters.Add(new MySqlParameter("id", id));
                    command.Parameters.Add(new MySqlParameter("descripcio", descripcio));
                    command.Parameters.Add(new MySqlParameter("id_torneig", idTorneig));
                    //command.Parameters.Add(new MySqlParameter("preinscripcion", p));
                    command.Parameters.Add(new MySqlParameter("caramboles_vistoria", carambolesVistoria));
                    command.Parameters.Add(new MySqlParameter("limit_entrades", limitEntrades));
                    //command.Parameters.Add(new MySqlParameter("mismo_grupo", mismogrupo));
                    //command.Parameters.Add(new MySqlParameter("id_partida", null));
                    command.ExecuteNonQuery();



                    //insert into grup values(14, 3, "grupo master sin participacones en otros torneos", 120, 50, null, null);
                }
            }
            catch (MySqlException e)
            {
                Debug.WriteLine("ERROR BD INSERT GRUPO:  " + e.Message);
            }

        }




        public int getInsertIdTorneo()
        {
            int index = 0;
            try
            {

                EncodingProvider provider = CodePagesEncodingProvider.Instance;
                Encoding.RegisterProvider(provider);


                using (Connection = new MySqlConnection(ConnectionString))
                {
                    Connection.Open();
                    MySqlCommand command = Connection.CreateCommand();
                    command.CommandText = "select max(comptador) from comptadors where upper(clau) = 'TORNEIG'";
                    // command.CommandText = "select max(Id) from torneo ";
                    using (MySqlDataReader reader = command.ExecuteReader())
                    {

                        if (reader.Read())
                        {
                            index = reader.GetInt16("max(comptador)") + 1;
                            // index = reader.GetInt16("max(Id)") + 1;
                        }
                    }
                }
            }
            catch (MySqlException e)
            {
                Debug.WriteLine("ERROR GET ID TORNEO SEGUENT BD:  " + e.Message);
            }
            ActualitzarComptadorsTorneo(index);
            return index;
        }




        private void ActualitzarComptadorsTorneo(int index)
        {
            try
            {
                EncodingProvider provider = CodePagesEncodingProvider.Instance;
                Encoding.RegisterProvider(provider);
                using (Connection = new MySqlConnection(ConnectionString))
                {

                    Connection.Open();
                    MySqlCommand command = Connection.CreateCommand();
                    command.CommandText = "update comptadors set comptador = @index where upper(clau) = 'TORNEIG'";
                    command.Parameters.Add(new MySqlParameter("index", index));
                    command.ExecuteNonQuery();
                }
            }
            catch (MySqlException e)
            {
                Debug.WriteLine("ERROR BD actualitzarComptadorsTorneo  " + e.Message);
            }
        }



        private void ActualitzarComptadorsGrup(int index)
        {
            try
            {
                EncodingProvider provider = CodePagesEncodingProvider.Instance;
                Encoding.RegisterProvider(provider);
                using (Connection = new MySqlConnection(ConnectionString))
                {
                    Connection.Open();
                    MySqlCommand command = Connection.CreateCommand();
                    command.CommandText = "update comptadors set comptador = @index where upper(clau) = 'GRUP'";
                    command.Parameters.Add(new MySqlParameter("index", index));
                    command.ExecuteNonQuery();
                }
            }
            catch (MySqlException e)
            {
                Debug.WriteLine("ERROR BD actualitzarComptadorsGrup  " + e.Message);
            }
        }



        public bool ActualizaPartida(Partida p, int id_grup)
        {
            try
            {
                EncodingProvider provider = CodePagesEncodingProvider.Instance;
                Encoding.RegisterProvider(provider);
                using (Connection = new MySqlConnection(ConnectionString))
                {

                    Connection.Open();
                    MySqlCommand command = Connection.CreateCommand();
                    command.CommandText = @"update partida set carambolesA = @carambolesA, carambolesB = @carambolesB,
                                          soci_a = @inscritA, soci_b = @inscritB, num_entrades= @numEntrades, 
                                          estat_partida = @estatPartida , guanyador=@guanador ,motiu_victoria=@motivo
                                          where id = @id_partida";
                    command.Parameters.Add(new MySqlParameter("carambolesA", p.CarambolesA));
                    command.Parameters.Add(new MySqlParameter("carambolesB", p.CarambolesB));
                    command.Parameters.Add(new MySqlParameter("inscritA", p.InscritA));
                    command.Parameters.Add(new MySqlParameter("inscritB", p.InscritB));
                    command.Parameters.Add(new MySqlParameter("numEntrades", p.NumEntrades));
                    command.Parameters.Add(new MySqlParameter("estatPartida", p.EstatPartida));
                    command.Parameters.Add(new MySqlParameter("guanador", p.ganador));
                    command.Parameters.Add(new MySqlParameter("motivo", p.MotiuVictoria));
                    command.Parameters.Add(new MySqlParameter("id_partida", p.Id));
                    command.ExecuteNonQuery();
                }
            }
            catch (MySqlException e)
            {
                Debug.WriteLine("ERROR Actualitzant Prtida BD:  " + e.Message);
                return false;
            }
            return true;
        }


        public void deleteTorneo(int IdTorneo, out string misstageError)
        {
            misstageError = "";
            try
            {
                EncodingProvider provider = CodePagesEncodingProvider.Instance;
                Encoding.RegisterProvider(provider);

                using (Connection = new MySqlConnection(ConnectionString))
                {

                    Connection.Open();
                    MySqlCommand command = Connection.CreateCommand();
                    command.CommandText = "delete from torneig where id = @Id";
                    command.Parameters.Add(new MySqlParameter("Id", IdTorneo));
                    command.ExecuteNonQuery();
                }
            }
            catch (MySqlException e)
            {
                Debug.WriteLine("ERROR BD deletePerit:  " + e.Message);
                misstageError = "No es possible esborrar aquest torneig, ja que ja te Grups  assignats";
            }
        }


        public void tancarTorneig(Torneo t)
        {
            try
            {
                EncodingProvider provider = CodePagesEncodingProvider.Instance;
                Encoding.RegisterProvider(provider);
                using (Connection = new MySqlConnection(ConnectionString))
                {

                    Connection.Open();
                    MySqlCommand command = Connection.CreateCommand();
                    command.CommandText = "update torneig set preinscripcio_open=1 where id=@torneo";
                    command.Parameters.Add(new MySqlParameter("torneo", t.Id));
                    command.ExecuteNonQuery();
                    Connection.Close();
                }
            }
            catch (MySqlException e)
            {
                Debug.WriteLine("ERROR BD TANCAR TORNEO  " + e.Message);
            }
        }


        public int tancarPartides(int id)
        {
            int index = 0;
            try
            {

                EncodingProvider provider = CodePagesEncodingProvider.Instance;
                Encoding.RegisterProvider(provider);
                using (Connection = new MySqlConnection(ConnectionString))
                {
                    Connection.Open();
                    MySqlCommand command = Connection.CreateCommand();
                    command.CommandText = "select count(*) from partida where estat_partida=1 and torneig_id=@torneo ";
                    command.Parameters.Add(new MySqlParameter("torneo", id));
                    command.ExecuteNonQuery();
                    Connection.Close();
                    using (MySqlDataReader reader = command.ExecuteReader())
                    {
                        if (reader.Read())
                        {
                            index = reader.GetInt16("count(*)");
                        }
                    }
                }
            }
            catch (MySqlException e)
            {
                Debug.WriteLine("ERROR BD TANCAR TORNEO  " + e.Message);
            }
            return index;
        }
    }
}


























































