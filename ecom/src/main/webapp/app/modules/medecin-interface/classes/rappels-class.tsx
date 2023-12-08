import { plainToClass } from "class-transformer"; 
import { Medecin } from "./medecin-class";

export class RappelData { 
    id: number; 
    date: string; 
    commentaire: string; 
    effectue: boolean;
    medecin: Medecin; 
    
}

export function donneesRappel(jsonObject) {
    
    return(plainToClass(RappelData, jsonObject))

}

export default donneesRappel;
