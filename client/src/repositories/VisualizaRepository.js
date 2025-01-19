import HTTP from "@/common/http";

const resource = "/visualizaciones";


export default {

    async findOneByUserAndGP(idGP) {
        return (await HTTP.get(`${resource}/${idGP}`));
      },
    
      async save(idGP) {
        const response = ((await HTTP.post(`${resource}`, idGP)));
        return response;
      },
    
      async delete(idGP) {
        return await HTTP.delete(`${resource}/${idGP}`).data;
      }

}; 