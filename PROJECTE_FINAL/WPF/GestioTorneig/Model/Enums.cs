using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GestioTorneig.Model
{
    public class Enums
    {

        public enum MOTIU_VICTORIA
        {
            PER_CARAMBOLES,
            ENTRADES_ASSOLIDES,
            ABANDONAMENT           
        }

        public static MOTIU_VICTORIA getMotiuVictoriaFromString(String estatSinistre)
        {
            switch (estatSinistre)
            {
                case "PER_CARAMBOLES":
                    return MOTIU_VICTORIA.PER_CARAMBOLES;
                case "ENTRADES_ASSOLIDES":
                    return MOTIU_VICTORIA.ENTRADES_ASSOLIDES;
                case "ABANDONAMENT":
                    return MOTIU_VICTORIA.ABANDONAMENT;
                default: return MOTIU_VICTORIA.PER_CARAMBOLES;
            }
        }


        public enum GUANYADOR
        {
           A,
           B
        }

        

        public enum ESTAT_PARTIDA
        {
            PENDENT,
            JUGADA
        }

        public static ESTAT_PARTIDA getEstatPartidaFromString(String estatPartida)
        {
            switch (estatPartida)
            {
                case "PENDENT":
                    return ESTAT_PARTIDA.PENDENT;
                case "JUGADA":
                    return ESTAT_PARTIDA.JUGADA;                 
                default: return ESTAT_PARTIDA.PENDENT;

            }
        }

      


    }
    }
