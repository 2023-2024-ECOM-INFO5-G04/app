import React from "react"
import { useState } from "react"



export const Filtre = () => {
    const [isOpen, setIsOpen] = useState<Boolean>(false)
    const [filtresSelectionnes, setFiltresSelectionnes] = useState<String[]>([])

    function handleDoubleClick(toPush : string){
        if (filtresSelectionnes.length == 0) {
            setFiltresSelectionnes([toPush])
        }
        else {
            setFiltresSelectionnes([...filtresSelectionnes,toPush])
        }


    }

    function handleClick(toRemove : string){
        if (filtresSelectionnes.length == 0) {
            return
        }
        else {
            if (filtresSelectionnes.includes(toRemove)){
                const filteredFiltres = filtresSelectionnes.filter((filter) => filter !== toRemove)
                setFiltresSelectionnes(filteredFiltres)
            }
        }


    }

    let msg : string;
    if (filtresSelectionnes.length== 0) {
        msg = "Aucun filtre séléctionné"}
    else {
        msg = "filtres séléctionnées : "
        filtresSelectionnes.map((filtre)=>{
            msg += filtre +'\n'
        })
    }
       

    return isOpen ? (
        <div>
			<button
				onClick={() => setIsOpen(false)}
			>
				Fermer les filtres
            </button>
            <button
                onDoubleClick={()=>handleDoubleClick('a')}
                onClick={()=>handleClick('a')}>
                a
            </button>
            <button
                onDoubleClick={()=>handleDoubleClick('b')}
                onClick={()=>handleClick('b')}>
                b
            </button>
          {msg}

        </div>

    ) : (
        <div>
            <button
                onClick={() => setIsOpen(true)}
            >
                Filtrer
            </button>
            {msg}
        </div>

    )
}

export default Filtre