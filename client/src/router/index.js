import { createRouter, createWebHistory } from "vue-router";
import NoteList from "../components/NoteList.vue";
import NoteForm from "../components/NoteForm.vue";
import NoteDetail from "../components/NoteDetail.vue";
import ErrorNotFoundView from "../views/ErrorNotFoundView.vue";
import LoginForm from "../components/LoginForm.vue";
import RegisterForm from "@/components/RegisterForm.vue";
import UserList from "@/components/UserList.vue";
import UserProfile from "@/components/UserProfile.vue";
import CircuitoIndividual from "@/components/CircuitoIndividual.vue";
import Circuitos from "@/components/Circuitos.vue";
import EditProfile from "@/components/EditProfile.vue";
import Pilotos from "@/components/Pilotos.vue";
import Constructores from "@/components/Constructores.vue";
import EditCircuitImage from "@/components/EditCircuitImage.vue";
import EditPilotImage from "@/components/EditPilotImage.vue";
import EditConstructorImage from "@/components/EditConstructorImage.vue";
import auth from "@/common/auth";
import { getStore } from "@/common/store";



const routes = [
  {
    path: "/circuitos/:circuitoId",
    name: "CircuitoId",
    component: CircuitoIndividual,
    meta: { public: true } // Ahora es pública
  },
  {
    path: '/',
    name: 'Circuitos',
    component: Circuitos,
    meta: { public: true }
  },
  {
    path: '/edit/circuit/:circuitoId',
    name: 'EditCircuitImage',
    component: EditCircuitImage,
    props: true,
  },
  {
    path: '/edit/pilotos/:pilotoId',
    name: 'EditPilotImage',
    component: EditPilotImage,
    props: true,
  },
  {
    path: '/edit/constructor/:constructorId',
    name: 'EditConstructorImage',
    component: EditConstructorImage,
    props: true,
  },
  {
    path: "/login",
    name: "Login",
    component: LoginForm,
    meta: { public: true, isLoginPage: true }
  },
  {
    path: "/register",
    name: "Register",
    component: RegisterForm,
    meta: { public: true }
  },
  {
    path: "/users",
    name: "Users",
    component: UserList,
    meta: { public: true }
  },
  {
    path: "/profile/:noteId",
    name: "profile",
    component: UserProfile
  },
  {
    path: "/profile/:profileId/edit",
    name: "EditProfile",
    component: EditProfile,
    props: true,
  },
  {
    path: '/pilotos',
    name: 'Pilotos',
    component: Pilotos,
    meta: { public: true }
  },
  {
    path: '/constructores',
    name: 'Constructores',
    component: Constructores,
    meta: { public: true }
  },
  {
    path: "/notes",
    name: "NoteList",
    component: NoteList
  },
  // /notes/new debe colocarse antes de /notes/:id porque si no vue-router
  // interpreta "new" como si fuera el id.
  //
  // Una forma de evitar este problema es usar una expresión regular para
  // limitar los valores que son interpretados. Por ejemplo, usando el path
  // /notes/:id(\\d+), vue-router espera que :id sea numérico.
  {
    path: "/notes/new",
    name: "NoteCreate",
    component: NoteForm
  },
  {
    path: "/notes/:noteId",
    name: "NoteDetail",
    component: NoteDetail
  },
  {
    path: "/notes/:noteId/edit",
    name: "NoteEdit",
    component: NoteForm
  },
  {
    path: "/:catchAll(.*)*",
    component: ErrorNotFoundView,
    meta: { public: true }
  }
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
});

router.beforeEach((to, from, next) => {
  // Lo primero que hacemos antes de cargar ninguna ruta es comprobar si
  // el usuario está autenticado (revisando el token)
  auth.isAuthenticationChecked.finally(() => {
    // por defecto, el usuario debe estar autenticado para acceder a las rutas
    const requiresAuth = !to.meta.public;

    const requiredAuthority = to.meta.authority;
    const userIsLogged = getStore().state.user.logged;
    const loggedUserAuthority = getStore().state.user.authority;

    if (requiresAuth) {
      // página privada
      if (userIsLogged) {
        if (requiredAuthority && requiredAuthority != loggedUserAuthority) {
          // usuario logueado pero sin permisos suficientes, le redirigimos a la página de login
          alert("Acceso prohibido para el usuario actual; intenta autenticarte de nuevo");
          auth.logout();
          next("/");
        } else {
          // usuario logueado y con permisos adecuados
          next();
        }
      } else {
        // usuario no está logueado, no puede acceder a la página
        alert("Esta página requiere autenticación");
        next("/");
      }
    } else {
      // página pública
      if (userIsLogged && to.meta.isLoginPage) {
        // si estamos logueados no hace falta volver a mostrar el login
        next({ name: "NoteList", replace: true });
      } else {
        next();
      }
    }
  });
});

export default router;