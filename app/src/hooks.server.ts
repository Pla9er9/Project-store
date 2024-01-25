// import { SECRET_KEY } from '$env/static/private';
// import { redirect } from '@sveltejs/kit';
// import jwt from 'jsonwebtoken';

// // const securedRoutes: string[] = [
// //     "change-password", "edit-account", "new-project"
// // ]

// export async function handle({ event, resolve }) {
//     const securedRoute = event.url.pathname === "/new-project"
//     console.log(event.url.pathname)
// //     console.log(event.url.pathname)
// //     for (let i = 0; i < securedRoutes.length; i++) {
// //         if (event.url.pathname.startsWith("/" + securedRoutes[i])) {
// //             securedRoute = true
// //             break
// //         }
// //     }

//     if (!securedRoute) {
//         const response = await resolve(event);
//         return response;
//     }

//     const authToken = event.cookies.get("jwtToken")
//     if (!authToken) {
//         throw redirect(302, "/login")
//     }

//     const claims = jwt.verify(authToken, SECRET_KEY);

//     if(authToken && claims){
//         const response = await resolve(event);
//         return response;
//     }
//     throw redirect(302, "/login")
// }
